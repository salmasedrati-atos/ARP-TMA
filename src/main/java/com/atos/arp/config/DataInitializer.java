package com.atos.arp.config;


import com.atos.arp.models.Role;
import com.atos.arp.models.User;
import com.atos.arp.repositories.RoleRepository;
import com.atos.arp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            // Création des rôles si absents
            if (roleRepo.count() == 0) {
                roleRepo.saveAll(List.of(
                        new Role("SUPERADMIN"),
                        new Role("ADMIN"),
                        new Role("USER")
                ));
            }

            // Création superadmin si absent
            if(userRepo.findByLogin("salma").isEmpty()) {
                User superAdmin = new User();
                superAdmin.setName("Salma");
                superAdmin.setFirstName("Sedrati");
                superAdmin.setLogin("s.sedrati");
                superAdmin.setPassword(encoder.encode("pass"));
                superAdmin.setEmail("salmasedrati22@gmail.com");
                superAdmin.setRole(roleRepo.findByName("SUPERADMIN"));
                superAdmin.setDateCreation(LocalDateTime.now());
                userRepo.save(superAdmin);
            }
        };
    }
}