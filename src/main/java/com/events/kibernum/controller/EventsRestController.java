package com.events.kibernum.controller;

import com.events.kibernum.dto.EventUserDTO;
import com.events.kibernum.model.Events;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/eventos")
public class EventsRestController {

    @Autowired
    private EventsRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{eventId}")
    public ResponseEntity<EventUserDTO> getEventAndUser(@PathVariable UUID eventId, @RequestBody UUID userId) {
        Events event = eventRepository.findById(eventId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);
        System.out.println("event: " + eventId);
        if (event == null || user == null) {
            return ResponseEntity.notFound().build();
        }

        EventUserDTO eventUserDTO = new EventUserDTO(event, user);
        return ResponseEntity.ok(eventUserDTO);
    }
    // public ResponseEntity<EventUserDTO> getEventAndUser(@PathVariable UUID eventId, @RequestBody UUID userId) {
    //     Events event = eventRepository.findById(eventId).orElse(null);
    //     Users user = userRepository.findById(userId).orElse(null);
    //     System.out.println("event: " + event);
    //     if (event == null || user == null) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     EventUserDTO eventUserDTO = new EventUserDTO(event, user);
    //     return ResponseEntity.ok(eventUserDTO);
    // }


    @GetMapping("/{eventId}")
    public List<Events> getMethodName(@PathVariable UUID eventId) {
        Events event = eventRepository.findById(eventId).orElse(null);
        System.out.println( "event: " + event.getTitle());
        List<Events> eventsList = new ArrayList<>();
        if (event != null) {
            eventsList.add(event);
        }
        return eventsList;
    }
    @DeleteMapping("/{eventId}")
    public List<Events> deleteEvent(@PathVariable UUID eventId) {
        Events event = eventRepository.findById(eventId).orElse(null); 
        System.out.println("deleting event " + event.getTitle());
        if (event != null) {
            eventRepository.delete(event);
        }
        List<Events> events = eventRepository.findAll();
        return events;
        
    }
    
    
}
