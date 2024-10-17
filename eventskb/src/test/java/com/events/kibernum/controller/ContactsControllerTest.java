package com.events.kibernum.controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.events.kibernum.model.Contacts;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ContactsControllerTest {

    @InjectMocks
    private ContactsController contactsController;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private UserRepository userRepository;

    private UUID userId;
    private Users user;
    private Contacts contact;

    @Before
    public void setUp() {
        userId = UUID.randomUUID();
        user = new Users();
        user.setId(userId);

        contact = new Contacts();
        contact.setId(UUID.randomUUID());
        contact.setNombre("John Doe");
        contact.setEmail("john.doe@example.com");
        contact.setUser(user);
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateContact_Success() {       
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setId(userId);

        Contacts contact = new Contacts();
        contact.setNombre("John Doe");
        contact.setEmail("johndoe@example.com");

        // Configurar los mocks correctamente
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(contactsRepository.save(any(Contacts.class))).thenReturn(contact);

        // Ejecutar el controlador
        ResponseEntity<Contacts> response = contactsController.createContact(userId, contact);

        // Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getNombre());
    }

    @SuppressWarnings("null")
    @Test
    public void testGetContactsByUserId_Success() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setId(userId);

        Contacts contact = new Contacts();
        contact.setNombre("John Doe");
        contact.setEmail("johndoe@example.com");

        List<Contacts> contactsList = List.of(contact);

        // Configurar los mocks correctamente
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(contactsRepository.findByUserId(userId)).thenReturn(contactsList);

        // Ejecutar el controlador
        ResponseEntity<List<Contacts>> response = contactsController.getContactsByUserId(userId);

        // Verificar la respuesta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getNombre());
    }

}
