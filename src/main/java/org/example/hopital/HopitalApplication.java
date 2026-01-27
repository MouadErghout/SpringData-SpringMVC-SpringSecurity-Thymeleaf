package org.example.hopital;

import org.example.hopital.entities.Patient;
import org.example.hopital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository PatientRepo;

    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Lombok NoArgsConstructor & AllArgsConstructor
        /*Patient p = new Patient();
        p.setId(null);
        p.setName("Mouad");
        p.setDateNaissance(new Date());
        p.setMalade(true);
        p.setScore(200);

        Patient p2 = new Patient(null,"Ahmed",new Date(),false,120);

        //Builder
        Patient p3 = Patient.builder()
                .name("Zakaria")
                .dateNaissance(new Date())
                .malade(true)
                .score(800)
                .build();*/
        PatientRepo.save(new Patient(null,"Mouad",new Date(),false,100));
        PatientRepo.save(new Patient(null,"Ahmed",new Date(),false,200));
        PatientRepo.save(new Patient(null,"Jawad",new Date(),false,300));

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
