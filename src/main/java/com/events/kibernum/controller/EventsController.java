package com.events.kibernum.controller;

// import com.events.kibernum.dto.EventUserDTO;
import java.time.LocalDateTime;
import java.util.UUID;

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
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;


// import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class EventsController {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Users user = userRepository.findByEmail(currentEmail);
        if (user != null) {
            // System.out.println("USUARIO ENCONTRADO: " + user.getName());
            model.addAttribute("user", user);
        }
        return "create_event";
    }
    
    
    // @PostMapping("/events/{eventId}")
    // public ResponseEntity<EventUserDTO> getEventAndUser(@PathVariable UUID eventId, @RequestParam UUID userId) {
    //     // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     System.out.println("userID==>" + userId);
    //     Events event = eventsRepository.findById(eventId).orElse(null);
    //     Users user = userRepository.findById(userId).orElse(null);

    //     if (event == null || user == null) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     EventUserDTO eventUserDTO = new EventUserDTO(event, user);
    //     System.out.println(eventUserDTO);
    //     return ResponseEntity.ok(eventUserDTO);
    // }
    
    @PostMapping("/events/new")
    public String createEvent(@RequestParam String title, 
                              @RequestParam String description,
                              @RequestParam String location,
                              @RequestParam String dateTime, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        // if (principal instanceof UserDetails) {
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
                // System.out.println( user);
                // model.addAttribute("user", user);
                return "redirect:/events";
            // }
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
        System.out.println("Evento modificado==>"+ id );
        model.addAttribute("events", eventsRepository.findAll());
        model.addAttribute("user", user);
        return "events";
    }

    @GetMapping("/events")
    public String listEvents(Model model) { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        // if (principal instanceof UserDetails) {
            String currentEmail = ((UserDetails) principal).getUsername();
            Users user = userRepository.findByEmail(currentEmail);
            if (user != null) {
                model.addAttribute("user", user);
            }
        // }
        model.addAttribute("events", eventsRepository.findAll());
        return "events";
    }
}
