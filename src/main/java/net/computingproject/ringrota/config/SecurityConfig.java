package net.computingproject.ringrota.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize //authorize any request that matches below

                        .requestMatchers("/", "/about-us/**", "/contact-us/**", "/css/**", "/js/**", "/img/**", "/fonts/**").permitAll()
                        .requestMatchers("/admin/**", "/admin-rota/**").hasRole("ADMIN") //change admin rota later
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/login").permitAll()
                                .defaultSuccessUrl("/home",true)


                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/") //after logging out, go to the home page
                );



        return http.build();


    }

    //method that hashes the passwords
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}



