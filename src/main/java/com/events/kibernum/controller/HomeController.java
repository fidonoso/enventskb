package com.events.kibernum.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName(); // Email del usuario autenticado
        // System.out.println("currentEmail: " + currentEmail);
        // System.out.println("currentEmail: " + authentication.getPrincipal());
        Users user = userRepository.findByEmail(currentEmail);
        if (user != null) {
            // System.out.println("USUARIO ENCONTRADO: " + user.getName());
            model.addAttribute("user", user);
        }

        return "home"; // Nombre de la plantilla de la vista (e.g., home.html)
    }
}
