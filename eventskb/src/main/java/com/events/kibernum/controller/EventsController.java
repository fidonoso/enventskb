package com.events.kibernum.controller;

// import com.events.kibernum.dto.EventUserDTO;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.events.kibernum.model.Events;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;

@Controller
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Users user = userRepository.findByEmail(currentEmail);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "create_event";
    }
    
    
    @PostMapping("/events/new")
    public String createEvent(@RequestParam String title, 
                              @RequestParam String description,
                              @RequestParam String location,
                              @RequestParam String dateTime, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
            String currentEmail = ((UserDetails) principal).getUsername();
            Users user = userRepository.findByEmail(currentEmail);
            if (user != null) {
                Events event = new Events();
                event.setTitle(title);
                event.setDescription(description);
                event.setLocation(location);
                event.setDateTime(LocalDateTime.parse(dateTime));
                event.setUser(user);
                eventsRepository.save(event);
                model.addAttribute("event", event);
                logger.info("Nuevo evento creador {}", title);
                return "redirect:/events";
        }
        return "redirect:/login";
    }

    @PostMapping("/events/edit/{id}")
    public String putMethodName(@PathVariable UUID id, 
                                @RequestParam String title, 
                                @RequestParam String description,
                                @RequestParam String location,
                                @RequestParam String dateTime, Model model) {
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String currentEmail = ((UserDetails) principal).getUsername();
        Users user = userRepository.findByEmail(currentEmail);
        Events event = eventsRepository.findById(id).orElse(null);
        // Events newevent = new Events();
                event.setTitle(title);
                event.setDescription(description);
                event.setLocation(location);
                event.setDateTime(LocalDateTime.parse(dateTime));
                event.setUser(user);
                eventsRepository.save(event);
        logger.info("Evento editado {}", event.getTitle());
        model.addAttribute("events", eventsRepository.findAll());
        model.addAttribute("user", user);
        return "events";
    }

    @GetMapping("/events")
    public String listEvents(Model model) { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String currentEmail = ((UserDetails) principal).getUsername();
        Users user = userRepository.findByEmail(currentEmail);
        model.addAttribute("user", user);
        model.addAttribute("events", eventsRepository.findAll());
        model.addAttribute("contacts", contactsRepository.findByUserId(user.getId()));
        return "events";
    }
}
