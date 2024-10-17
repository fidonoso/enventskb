package com.events.kibernum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;


@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName(); // Email del usuario autenticado
        Users user = userRepository.findByEmail(currentEmail);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("events", eventsRepository.findAll());
        model.addAttribute("contacts", contactsRepository.findByUserId(user.getId()));
        }

        return "home"; 
    }
}
