package com.lautaro.spring_boot_microservice_3_api_gateway.service;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.Role;
import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void chanageRole (Role newRole, String username);
}
