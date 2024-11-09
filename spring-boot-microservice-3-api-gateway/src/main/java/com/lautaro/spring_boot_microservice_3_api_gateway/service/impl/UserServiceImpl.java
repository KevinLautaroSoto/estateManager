package com.lautaro.spring_boot_microservice_3_api_gateway.service.impl;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.Role;
import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
import com.lautaro.spring_boot_microservice_3_api_gateway.repository.UserRepository;
import com.lautaro.spring_boot_microservice_3_api_gateway.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional //siempre que se use una sentencia SQL directa que modifique data directamente de una tabla como en el repository de este caso
    @Override
    public void chanageRole(Role newRole, String username) {
        userRepository.updateUserRole(username, newRole);
    }


}
