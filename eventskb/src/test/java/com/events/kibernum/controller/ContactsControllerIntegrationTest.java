package com.events.kibernum.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.events.kibernum.model.Contacts;
import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactsRepository contactsRepository;

    @MockBean
    private UserRepository userRepository;

    private Users testUser;
    private Contacts testContact;

    @BeforeEach
    public void setUp() {
        testUser = new Users();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@gmail.com");
        testUser.setName("Pedrito");
        testUser.setLast_name("Donoso");
        testUser.setPassword("user123456");

        testContact = new Contacts();
        testContact.setId(UUID.randomUUID());
        testContact.setNombre("Test Contact");
        testContact.setUser(testUser);
        testContact.setEmail("contact@gmail.com");
    }

    @Test
    public void testGetContactsByUserId() throws Exception {
        // Configurar el comportamiento del mock para UserRepository
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        // Configurar el comportamiento del mock para ContactsRepository
        when(contactsRepository.findByUserId(testUser.getId())).thenReturn(Arrays.asList(testContact));

        // Realizar la solicitud y capturar el resultado
        MvcResult result = mockMvc.perform(get("/contacts/user/{userId}", testUser.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Test Contact"))
            .andExpect(jsonPath("$[0].email").value("contact@gmail.com"))
            .andReturn();

        // Imprimir información de depuración
        System.out.println("Response status: " + result.getResponse().getStatus());
        System.out.println("Response content: " + result.getResponse().getContentAsString());
    }
}