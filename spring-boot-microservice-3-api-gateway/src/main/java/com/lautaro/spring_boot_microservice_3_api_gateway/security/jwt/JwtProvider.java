package com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt;

import com.lautaro.spring_boot_microservice_3_api_gateway.security.UserPrincipal;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);
}
