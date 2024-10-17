package com.events.kibernum.controller;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.ContactsRepository;
import com.events.kibernum.repository.EventsRepository;
import com.events.kibernum.repository.UserRepository;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventsRepository eventsRepository;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        // Configurar un ViewResolver simulado
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
    
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(viewResolver)
                .build();
    }


    @Test
    public void testHome_UserExists() throws Exception {
        // Simular la autenticación del usuario
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Simular la autenticación con un correo de usuario
        when(authentication.getName()).thenReturn("test@example.com");

        // Simular la búsqueda de un usuario por email
        Users user = new Users();
        user.setId(new java.util.UUID(0, 1));
        user.setEmail("test@example.com");
        user.setName("Test User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Simular los métodos de eventos y contactos
        when(eventsRepository.findAll()).thenReturn(new ArrayList<>()); // Aquí puedes simular una lista de eventos
        when(contactsRepository.findByUserId(user.getId())).thenReturn(new ArrayList<>()); // Simular una lista vacía de contactos

        // Ejecutar la prueba con MockMvc
        mockMvc.perform(get("/home"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("events"))
            .andExpect(model().attributeExists("contacts"));
    }

    @Test
    public void testHome_UserNotFound() throws Exception {
        // Simular la autenticación del usuario
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Simular la autenticación con un correo de usuario
        when(authentication.getName()).thenReturn("notfound@example.com");

        // Simular que no se encuentra el usuario
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        // Ejecutar la prueba con MockMvc
        mockMvc.perform(get("/home"));
  
    }
}