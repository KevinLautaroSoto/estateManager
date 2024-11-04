package com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    //Captura el request que esta enviando el cliente antes que llegue a la logica del backend del servidor y evalúa si cumple las reglas de validación impuestas
    //Si no las cumple se "detiene" el request y envia un response con un throw exception indicando al cliente que el servidor no puede procesar su pedido ya que no cumple con las reglas de autenticación.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Obtiene la autenticación del usuario a partir del token JWT en la solicitud.
        Authentication authentication = jwtProvider.getAuthentication(request);

        //Verifica si se obtuvo una autenticación y si el token es válido.
        if (authentication != null && jwtProvider.isTokenValid(request)) {
            //Si el token es válido, establece la autenticación en el contexto de seguridad de Spring.
            //Lo que permite que Spring security conozca al usuario autenticado y sus roles.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Continúa la cadena de filtros, permitiendo que la solicitud llegue al siguiente filtro o al controlador.
        filterChain.doFilter(request, response);
    }
}
