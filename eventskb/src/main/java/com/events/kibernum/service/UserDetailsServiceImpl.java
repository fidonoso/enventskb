package com.events.kibernum.service;
// package com.events.kibernum.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Asegúrate de importar tu repositorio de usuario y la clase de usuario correctamente
// import com.tu.paquete.UserRepository;
// import com.tu.paquete.User; 
// Ajusta el paquete según tu estructura de proyecto

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private com.events.kibernum.repository.UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.events.kibernum.model.Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
