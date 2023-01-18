package projekt.carlease.springcarlease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import projekt.carlease.springcarlease.Klasy.CarImg;
import projekt.carlease.springcarlease.Klasy.Uzytkownik;
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
        return "loggedHomePage";
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("UserIn", new Uzytkownik());
        return "register";
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
}
