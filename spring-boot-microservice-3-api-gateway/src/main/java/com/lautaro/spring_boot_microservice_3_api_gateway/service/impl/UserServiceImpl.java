package com.lautaro.spring_boot_microservice_3_api_gateway.service.impl;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.Role;
import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
import com.lautaro.spring_boot_microservice_3_api_gateway.repository.UserRepository;
import com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.JwtProvider;
import com.lautaro.spring_boot_microservice_3_api_gateway.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;//implementada en main

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreationDate(LocalDateTime.now());

        User userCreated = userRepository.save(user);//Guardo al usuario creado en una nueva variable.

        String jwt = jwtProvider.generateToken(userCreated);//genero una nueva jwt para el usuario creado
        userCreated.setToken(jwt);//y agrego el token al usuario recientemente creado para devolverselo al cliente.

        return userCreated;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional //siempre que se use una sentencia SQL directa que modifique data directamente de una tabla como en el repository de este caso
    @Override
    public void changeRole(Role newRole, String username) {
        userRepository.updateUserRole(username, newRole);
    }

    @Override
    public User findByUsernameReturnToken(String username) { //Metodo que busca un usuario por su username y lo retorna con su token.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user doesn´t exits: " + username));

        String jwt = jwtProvider.generateToken(user);
        user.setToken(jwt);
        return user;
    }


}
