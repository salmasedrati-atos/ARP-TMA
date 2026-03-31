package com.atos.arp.services;

import com.atos.arp.models.User;
import com.atos.arp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    // Vérifie le login + mot de passe
    public User authenticate(String login, String password) {
        return userRepo.findByLogin(login)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .orElse(null);
    }
}
