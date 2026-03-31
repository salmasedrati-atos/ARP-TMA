package com.atos.arp.controllers;

import com.atos.arp.models.User;
import com.atos.arp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String password = body.get("password");

        User user = authService.authenticate(login, password);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Identifiants invalides");
        }

        // Retourne username + rôle pour Angular
        Map<String, String> res = new HashMap<>();
        res.put("login", user.getLogin());
        res.put("role", user.getRole().getName());

        return ResponseEntity.ok(res);
    }
}
