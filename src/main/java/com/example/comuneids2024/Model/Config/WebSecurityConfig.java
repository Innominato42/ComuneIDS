package com.example.comuneids2024.Model.Config;
import com.example.comuneids2024.Model.UtenteAutenticato;
import com.example.comuneids2024.Repository.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll() // Permetti tutte le richieste senza autenticazione
                )
                .csrf(AbstractHttpConfigurer::disable) // Disabilita CSRF per test/debug
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Disabilita frame options per H2 console
                .formLogin(AbstractHttpConfigurer::disable) // Disabilita il form login
                .httpBasic(AbstractHttpConfigurer::disable) // Disabilita l'autenticazione basic
                .logout(LogoutConfigurer::permitAll); // Permetti logout
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UtenteAutenticato utenteAutenticato = utenteAutenticatoRepository.findByUsername(username);
            if (utenteAutenticato != null) {
                return User
                        .withUsername(utenteAutenticato.getUsername())
                        .password(utenteAutenticato.getPassword())
                        .roles(utenteAutenticato.getRole().name())
                        .build();
            } else {
                throw new UsernameNotFoundException("Utente non trovato.");
            }
        };
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Definisci i percorsi che desideri configurare per CORS
                .allowedOrigins("*")  // Specifica gli origini consentiti (es. "*", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specifica i metodi HTTP consentiti
                .allowedHeaders("*");  // Specifica gli header consentiti
    }

}