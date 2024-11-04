package com.lautaro.spring_boot_microservice_3_api_gateway.security;

import com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Habilita la configuración de seguridad en esta aplicación.
@Configuration //Declara esta clase como una configuración de Spring
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; //Inyecta el servicio de detalles del usuario personalizado.

    @Autowired
    private PasswordEncoder passwordEncoder; //Inyecta el codificador de contraseñas.

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        //Método principal para configurar la cadena de filtros de seguridad
        http
                .csrf(AbstractHttpConfigurer::disable)//Desactiva CSRF, ya que no es necesario en APIs Restful
                .cors(AbstractHttpConfigurer::disable)//Desactiva CORS
                .authorizeHttpRequests(authorize -> authorize //Configura la autorización de solicitudes HTTP
                        .requestMatchers("/api/v1/authentication/sign-in", "/api/v1/authentication/sign-up").permitAll()
                        //Permite el acceso sin authentication a las rutas especificadas.
                        .anyRequest().authenticated()
                        // Requiere autenticacion para cualquier otra solicitud
                )
                .httpBasic(Customizer.withDefaults())//configura la autenticacion basica HTTP (pedirá usuario y contraseña en cada solicitud)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        //Configura la política de creación de sesiones como STATELESS
                        //Esto indica que no se debe guardar el estado de la sesión del usuario en el servidor.
                        //ideal para aplicaciónes si estado como una API RESTful.
                )
                .addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build(); //Construye y devuelve la cadena de filtros configurada
    }


    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }
}
