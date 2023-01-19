package projekt.carlease.springcarlease;

import java.io.File;
import java.sql.Date;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import projekt.carlease.springcarlease.Klasy.CarImg;
import projekt.carlease.springcarlease.Klasy.Samochod;
import projekt.carlease.springcarlease.Klasy.Uzytkownik;
import projekt.carlease.springcarlease.Klasy.Wypozyczenie;
import projekt.carlease.springcarlease.Repozytoria.CarImgRepo;
import projekt.carlease.springcarlease.Repozytoria.SamochodRepo;
import projekt.carlease.springcarlease.Repozytoria.UzytkownikRepo;
import projekt.carlease.springcarlease.Repozytoria.WypozyczenieRepo;

@Controller
public class Controllers  {                                 //tu będziesz miał wszystkie endpointy
    @Autowired
    private UzytkownikRepo uzytRepo;
    @Autowired
    private SamochodRepo samRepo;
    @Autowired
    private CarImgRepo imgRepo;
    @Autowired
    private WypozyczenieRepo wypRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping(value = "/")
    public String homePage(Model model, Authentication auth){
        if(auth==null){                                                     //jak nie zalogowany to na HomePage
            return "redirect:/HomePage";
        }else if(auth.getAuthorities().toString().equals("[ADMIN]")){       //jak admin to na stronę główną admina
            return "redirect:/AdminPage";
        }else if(auth.getAuthorities().toString().equals("[USER]")){        //jak user to na stronę zalogowanego usera
            return "redirect:/LoggedPage";
        }else{                                                              //jak jakiś błąd to na HomePage
            return "redirect:/HomePage";
        }
    }

    @GetMapping(value = "/HomePage")
    public String defaultHomePage(Model model){
        model.addAttribute("autaOut", samRepo.findAllByZarezerwowanyIs(0));
        return "defaultHomePage";
    }
    
    @GetMapping(value = "/AdminPage")
    public String adminHomePage(Model model){
        return "adminHomePage";
    }

    @GetMapping(value = "/LoggedPage")
    public String loggedHomePage(Model model){
        model.addAttribute("autaOut", samRepo.findAllByZarezerwowanyIs(0));
        return "loggedHomePage";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("UserIn", new Uzytkownik());             
        return "register";
    }

    @GetMapping(value = "/szukaj")
    public String szukaj(Model model, Authentication auth, String szukajMiasto){
        if(szukajMiasto!=""){
            model.addAttribute("autaOut", samRepo.findAllByZarezerwowanyIsAndMiastoIs(0, szukajMiasto));
        }else{
            model.addAttribute("autaOut", samRepo.findAllByZarezerwowanyIs(0));
        }
        if(auth!=null){
            return "loggedHomePage";
        }else{
            return "defaultHomePage";
        }
    }

    @PostMapping(value = "/register")
    public String register(Model model, Uzytkownik newUser){
        if(uzytRepo.findByEmail(newUser.getEmail())!=null){                         //jeśli email jest już w bazie wyświetla stosowny komunikat na stronie
            model.addAttribute("UserIn", newUser);
            model.addAttribute("errorUser", true);
            return "/register";
        }
        try{
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));     //szyfruje hasło
            newUser.setRole("USER");                                          //ustawia defaultowo role na USER
            uzytRepo.save(newUser);                                                 //zapisuje
            return("redirect:/login?newUser");                                      //przenosi do strony logowania
        }catch(Exception e){                                                        //jak coś się wywali podczas zapisu to wyświetla że coś poszło nie tak
            model.addAttribute("UserIn", newUser);
            model.addAttribute("errorOther", true);
            return "/register";
        }
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping(value = "/admin/dodajAuto")
    public String addNewCar(Model model){
        model.addAttribute("CarIn", new Samochod() );
        return "addNewCar";
    }

    @PostMapping(value = "/admin/dodajAuto")
    public String addNewCar(Model model, Samochod car, @RequestParam("zdjecia")MultipartFile[] files){
        try{
            Long id = samRepo.save(car).getId();                                                    //zapisujemy Samochod jednocześnie pobierając jego id
           for (MultipartFile f : files) {
            String pathSave = System.getProperty("user.dir")+"\\data\\cars\\"+id+"\\";              //ścieżka nie relatywna (żeby zapisać do folderu)
            String pathRel = "\\data\\cars\\"+id+"\\";                                              //ścieżka relatywna (żeby zapisać do bazy)
            File theDir = new File(pathSave);                                                       
                if (!theDir.exists()){                                                              //sprawdzamy czy ścieżka istnieje   
                    theDir.mkdirs();                                                                //jeśli nie to ją tworzymy
                }
                CarImg img = new CarImg(samRepo.findByIdIs(id),pathRel+f.getOriginalFilename());    //tworzymy obiekt z naszym id auta i ścierzką relacyjną
                imgRepo.save(img);                                                                  //zapisujemy obiekt do bazy
                f.transferTo(new File(pathSave+f.getOriginalFilename()));                           //przenosimy obiekt do stworzonego wcześniej folderu
           }
        }catch(Exception e){                                                                        //jak coś to walimy wyjątek
            System.out.println(e.getMessage());
        }
        return "addNewCar";
    }

    @GetMapping(value = "/user/rezerwuj/{id}")
    public String addNewCar(Model model, Authentication auth, @PathVariable("id") Long id){
        Samochod car = samRepo.findByIdIs(id);
        Uzytkownik user = uzytRepo.findByEmail(auth.getName());
        Wypozyczenie wypo = new Wypozyczenie(new Date(System.currentTimeMillis()),car,user);
        model.addAttribute("rezerwacjaIn", wypo);
        return "rezerwuj";
    }

    @PostMapping(value = "/user/rezerwuj")
    public String addNewCar(Model model,Wypozyczenie wypo){
        wypRepo.save(wypo);
        model.addAttribute("autaOut", samRepo.findAllByZarezerwowanyIs(0));
        model.addAttribute("success", true);
        return "loggedHomePage";
    }

    @GetMapping(value = "/user/mojeRezerwacje")
    public String addNewCar(Model model, Authentication auth){
        model.addAttribute("listaWypoAkt", wypRepo.findAllByUzytkownikEmailIsAndDataKoniecIsNull(auth.getName()));
        model.addAttribute("listaWypoHis", wypRepo.findAllByUzytkownikEmailIsAndDataKoniecIsNotNull(auth.getName()));
        return "mojeWypozyczenia";
    }

}
