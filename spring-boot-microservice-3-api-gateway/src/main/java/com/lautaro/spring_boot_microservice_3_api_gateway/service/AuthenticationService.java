package com.lautaro.spring_boot_microservice_3_api_gateway.service;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
