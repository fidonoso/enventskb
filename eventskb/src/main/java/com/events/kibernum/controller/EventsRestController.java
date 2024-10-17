package com.events.kibernum.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.kibernum.dto.EventUserDTO;
import com.events.kibernum.model.Events;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;


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
        if (event == null || user == null) {
            return ResponseEntity.notFound().build();
        }

        EventUserDTO eventUserDTO = new EventUserDTO(event, user);
        return ResponseEntity.ok(eventUserDTO);
    }

    @GetMapping("/{eventId}")
    public List<Events> getMethodName(@PathVariable UUID eventId) {
        Events event = eventRepository.findById(eventId).orElse(null);
        List<Events> eventsList = new ArrayList<>();
        if (event != null) {
            eventsList.add(event);
        }
        return eventsList;
    }
    @DeleteMapping("/{eventId}")
    public List<Events> deleteEvent(@PathVariable UUID eventId) {
        Events event = eventRepository.findById(eventId).orElse(null); 
        if (event != null) {
            eventRepository.delete(event);
        }
        List<Events> events = eventRepository.findAll();
        return events;
        
    }
    
    
}
