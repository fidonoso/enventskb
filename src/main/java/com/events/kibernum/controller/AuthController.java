package com.events.kibernum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    // @PostMapping("/register")
    // public String registerUser(@ModelAttribute Users user) {
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     userRepository.save(user);
    //     return "redirect:/login";
    // }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            // Manejo del error, redirigir a una página de error o mostrar un mensaje
            return "error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        userRepository.save(user);

        // si el registro sale bien, lo redirecciona a la pagina d elogin
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
