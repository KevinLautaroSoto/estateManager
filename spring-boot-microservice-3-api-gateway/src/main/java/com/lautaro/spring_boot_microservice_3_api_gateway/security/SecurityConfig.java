package com.lautaro.spring_boot_microservice_3_api_gateway.security;

import com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Declara esta clase como una configuración de Spring
@EnableWebSecurity // Habilita la configuración de seguridad en esta aplicación
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Servicio personalizado de detalles del usuario

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas


    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF, ya que no es necesario en APIs RESTful
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/authentication/sign-in", "/api/v1/authentication/sign-up").permitAll() // Permite acceso sin autenticación a rutas específicas
                        .requestMatchers(HttpMethod.GET, "/gateway/estate").permitAll()
                        .requestMatchers("/gateway/estate/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura política de sesión sin estado (ideal para APIs RESTful)
                )
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);// Agrega el filtro JWT antes del filtro de autenticación

        return http.build(); // Construye y devuelve la cadena de filtros configurada
    }

    // Bean para el filtro de autorización JWT
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }


}
