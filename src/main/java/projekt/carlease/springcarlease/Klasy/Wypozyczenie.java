package projekt.carlease.springcarlease.Klasy;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="wypozyczenie") 
public class Wypozyczenie {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Date dataStart;
    private Date dataKoniec;
    @ManyToOne
    private Samochod samochod;
    @ManyToOne
    private Uzytkownik uzytkownik;

    public Wypozyczenie() {
    }

    public Wypozyczenie(Date dataStart, Samochod samochod, Uzytkownik uzytkownik) {
        this.dataStart = dataStart;
        this.samochod = samochod;
        this.uzytkownik = uzytkownik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataStart() {
        return dataStart;
    }

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public Date getDataKoniec() {
        return dataKoniec;
    }

    public void setDataKoniec(Date dataKoniec) {
        this.dataKoniec = dataKoniec;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public void setSamochod(Samochod samochod) {
        this.samochod = samochod;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public double getDni(){
        double dni = (System.currentTimeMillis()-dataStart.getTime())/86400000;
        if(dni<1){dni=1;}
        return dni;
    }

    public double getDniWszystkie(){
        double dni = (dataKoniec.getTime()-dataStart.getTime())/86400000;
        if(dni<1){dni=1;}
        return dni;
    }

    @Override
    public String toString() {
        return "Wypozyczenie [id=" + id + ", dataStart=" + dataStart + ", dataKoniec=" + dataKoniec + ", samochod="
                + samochod.getId() + ", user=" + uzytkownik.getEmail() + "]";
    }
    
    
    
}
