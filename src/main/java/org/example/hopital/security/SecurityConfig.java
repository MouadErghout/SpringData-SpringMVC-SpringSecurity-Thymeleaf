package org.example.hopital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String pwd = passwordEncoder.encode("1234");
        return new InMemoryUserDetailsManager(
                User.withUsername("User1").password(pwd).roles("USER").build(),
                User.withUsername("User2").password(passwordEncoder.encode("22222")).roles("VISITOR").build(),
                User.withUsername("Admin").password(passwordEncoder.encode("admin")).roles("USER","ADMIN").build()
        );
    }

    @Bean
    public JdbcUserDetailsManager  jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // Créer mes propres utilisateurs et configurer les rôles pour accéder aux ressources de l'application
    // personnaliser la sécurité de l'application an appliquant des filtres
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(withDefaults());
//        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
//        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests((authorize -> authorize.anyRequest().authenticated()));
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");

        return httpSecurity.build();
    }
}
