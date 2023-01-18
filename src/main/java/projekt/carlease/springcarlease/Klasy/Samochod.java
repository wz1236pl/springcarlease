package projekt.carlease.springcarlease.Klasy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="samochod") 
public class Samochod {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nrRej;
    private String marka;
    private String model;
    private String typ;
    private String kolor;
    private int rok;
    private int moc;
    private int cena;
    private String miasto;
    @OneToMany(mappedBy = "samochod")
    private List<CarImg> zdj = new ArrayList<CarImg>();
    @OneToMany(mappedBy = "samochod")
    private List<Wypozyczenie> wypo = new ArrayList<Wypozyczenie>();
    
    public Samochod() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNrRej() {
        return nrRej;
    }
    public void setNrRej(String nrRej) {
        this.nrRej = nrRej;
    }
    public String getMarka() {
        return marka;
    }
    public void setMarka(String marka) {
        this.marka = marka;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getTyp() {
        return typ;
    }
    public void setTyp(String typ) {
        this.typ = typ;
    }
    public String getKolor() {
        return kolor;
    }
    public void setKolor(String kolor) {
        this.kolor = kolor;
    }
    public int getRok() {
        return rok;
    }
    public void setRok(int rok) {
        this.rok = rok;
    }
    public int getMoc() {
        return moc;
    }
    public void setMoc(int moc) {
        this.moc = moc;
    }
    public int getCena() {
        return cena;
    }
    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public List<CarImg> getZdj() {
        return zdj;
    }

    public void setZdj(List<CarImg> zdj) {
        this.zdj = zdj;
    }

    public List<Wypozyczenie> getWypo() {
        return wypo;
    }

    public void setWypo(List<Wypozyczenie> wypo) {
        this.wypo = wypo;
    }

    @Override
    public String toString() {
        return "Samochod [id=" + id + ", nrRej=" + nrRej + ", marka=" + marka + ", model=" + model + ", typ=" + typ
                + ", kolor=" + kolor + ", rok=" + rok + ", moc=" + moc + ", cena=" + cena + ", miasto=" + miasto
                + ", zdj=" + zdj + ", wypo=" + wypo + "]";
    }
    
}
