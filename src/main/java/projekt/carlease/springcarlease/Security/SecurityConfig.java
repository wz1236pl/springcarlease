package projekt.carlease.springcarlease.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@AutoConfigureBefore
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuth;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{                         //określa co kto może
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/register").permitAll()                                               //do logowania i rejestracji dajemy dostęp wszystkim
            .antMatchers("/login").permitAll()
            .antMatchers("/").permitAll()                                                       //na pusty endpoint też
            .antMatchers("/user/edytujDane").hasAnyAuthority("USER","ADMIN")    //do edycji własnego profilu dajemy dostęp adminowi i userowi
            .antMatchers("/user/**").hasAnyAuthority("USER")                    //ograniczamy dostęp tylko dla zalogowanego gościa (admin nie może rezerwować)
            .antMatchers("/admin/**").hasAnyAuthority("ADMIN")                  //to samo dla admina
            .and()                                                                                             //jak coś to "/**" to że po / wszystkie inne linki 
            .formLogin()
            .loginPage("/login");                                                                   //wywołuje ten endpoint jako podstawowy logowania
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuth);        
        return authenticationManagerBuilder.build();                                            //ustawiasz własnego providera dla logowania
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){                                       //nie pozwalasz żeby spring zabezpieczał ci konsole bazy(baza ma swoje zabezpieczenia)
        return (web) -> web.ignoring().antMatchers("/h2/**");
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){                                            //ustawiasz jak hashujesz hasła (jakim encoderem)
        return new BCryptPasswordEncoder();
    }

}
