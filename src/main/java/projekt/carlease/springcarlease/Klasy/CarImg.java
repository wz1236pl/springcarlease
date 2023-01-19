package projekt.carlease.springcarlease.Klasy;

import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="imgurl") 
public class CarImg {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Samochod samochod;
    private String url;
    
    public CarImg() {
    }

    public CarImg(Samochod samochod, String url) {
        this.samochod = samochod;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public void setSamochod(Samochod samochod) {
        this.samochod = samochod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CarImg [id=" + id + ", samochod=" + samochod.getId() + ", url=" + url + "]";
    }

    public String getImage() throws IOException {                                                   //Metoda do przestłu zdjęcia zakodowanego w Base64
        try{
            BufferedImage bImage = ImageIO.read(new File(System.getProperty("user.dir")+url));      //pobieramy plik ze ścieżki przechowywanej w url
            ByteArrayOutputStream bos = new ByteArrayOutputStream();                                //tworzymy nową tablice bajtów
            ImageIO.write(bImage, "jpg", bos );                                                     //konwersja z pliku do tablicy
            byte [] data = bos.toByteArray();                                                       //nw tak było w necie i kurwa nie mam pojęcia po chuj to(nie testowałem czy bez tego działa)
            return Base64.getEncoder().encodeToString(data);                                        //generujemy Stringa o podstawie 64 który opisuje nasz obrazek
        }catch(Exception e){                                                                        //jak coś spadnie z rowerka do walimy wyjątek
            return null;
        }
    }
    
}
