package com.lautaro.spring_boot_microservice_3_api_gateway.request;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //con esto puedo agregar objetos de tipo Bean dentro de la clase
public class FeignConfiguration { //Crear una conexión segura entre los microservicios utilizando una autenticación básica (BasicAuth)

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${service.security.sercure-key-username}") String username,
            @Value("${service.security.sercure-key-password}") String password
            ) {
        return new BasicAuthRequestInterceptor(username, password);
    }
}
