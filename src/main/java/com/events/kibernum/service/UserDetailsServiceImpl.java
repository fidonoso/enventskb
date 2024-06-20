package com.events.kibernum.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// Asegúrate de importar tu repositorio de usuario y la clase de usuario correctamente
// import com.tu.paquete.UserRepository;
// import com.tu.paquete.User; 
// Ajusta el paquete según tu estructura de proyecto

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private com.events.kibernum.repository.UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.events.kibernum.model.Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
