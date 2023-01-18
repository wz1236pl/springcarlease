package projekt.carlease.springcarlease.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import projekt.carlease.springcarlease.Klasy.User;
import projekt.carlease.springcarlease.Repozytoria.UserRepo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getName();                                                  // z form logowania pobiera login
        final String password = authentication.getCredentials().toString();                             // z form logowania pobiera hasło
        
        User user = userRepo.findByEmail(email);                                                        // szuka użytkownika z podanym w logowaniu emailem
        if(user == null){throw new UsernameNotFoundException("User not found");}                   // jeśli nie ma takiego w bazie wywala wyjątek
        if(passwordEncoder.matches(password, user.getPassword())){                                      // jak jest to sprawdza czy hasła się zgadzają 
            List<GrantedAuthority> authorities = new ArrayList<>();                                     // tworzy liste typu GrantedAuthority
            authorities.add(new SimpleGrantedAuthority(user.getRole()));                                // dodaje do listy uprawnień tego usera 
            return new UsernamePasswordAuthenticationToken(email, password, authorities);               // zwraca nam token po którym się autoryzuje
        }else{          
            throw new BadCredentialsException("Login error!");                                     // jeśli znalazło usera ale hasło się nie zgadza to wywala wyjątek
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
