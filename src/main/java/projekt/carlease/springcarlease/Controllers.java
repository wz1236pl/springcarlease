package projekt.carlease.springcarlease;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllers  {                                 //tu będziesz miał wszystkie endpointy
    @GetMapping(value = "/")
    public String homePage(Model model, Authentication auth){
        
        if(auth==null){
            return "defaultHomePage";
        }else if(auth.getAuthorities().toString().equals("[ADMIN]")){
            return "adminHomePage";
        }else{
            return "loggedHomePage";
        }
    }
}
