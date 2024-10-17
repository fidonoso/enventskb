package com.events.kibernum.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.kibernum.model.Contacts;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.UserRepository;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear un contacto para un usuario
    @PostMapping("/user/{userId}")
    public ResponseEntity<Contacts> createContact(@PathVariable UUID userId, @RequestBody  Contacts contact) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            contact.setUser(user.get());
            Contacts savedContact = contactsRepository.save(contact);
            return ResponseEntity.ok(savedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los contactos de un usuario por su ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Contacts>> getContactsByUserId(@PathVariable UUID userId) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Contacts> contacts = contactsRepository.findByUserId(userId);
            return ResponseEntity.ok(contacts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener un contacto por su ID
    @GetMapping("/{contactId}")
    public ResponseEntity<Contacts> getContactById(@PathVariable UUID contactId) {
        Optional<Contacts> contact = contactsRepository.findById(contactId);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un contacto
    @PutMapping("/{contactId}")
    public ResponseEntity<Contacts> updateContact(@PathVariable UUID contactId, @RequestBody Contacts contactDetails) {
        Optional<Contacts> contact = contactsRepository.findById(contactId);
        if (contact.isPresent()) {
            Contacts existingContact = contact.get();
            existingContact.setNombre(contactDetails.getNombre());
            existingContact.setEmail(contactDetails.getEmail());
            Contacts updatedContact = contactsRepository.save(existingContact);
            return ResponseEntity.ok(updatedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un contacto
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID contactId) {
        Optional<Contacts> contact = contactsRepository.findById(contactId);
        if (contact.isPresent()) {
            contactsRepository.delete(contact.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

