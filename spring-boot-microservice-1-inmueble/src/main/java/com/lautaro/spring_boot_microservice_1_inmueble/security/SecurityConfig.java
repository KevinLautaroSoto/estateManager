package com.lautaro.spring_boot_microservice_1_inmueble.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    //this info get it from application.properties
    @Value("${service.security.sercure-key-username}")
    private String SERCURE_KEY_USERNAME;

    @Value("${service.security.sercure-key-password}")
    private String SERCURE_KEY_PASSWORD;


    @Value("${service.security.sercure-key-username-2}")
    private String SERCURE_KEY_USERNAME_2;

    @Value("${service.security.sercure-key-password-2}")
    private String SERCURE_KEY_PASSWORD_2;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") //new form to configure the matching requests
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user1 = User.builder()
                .username(SERCURE_KEY_USERNAME)
                .password(passwordEncoder().encode(SERCURE_KEY_PASSWORD))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username(SERCURE_KEY_USERNAME_2)
                .password(passwordEncoder().encode(SERCURE_KEY_PASSWORD_2))
                .roles("DEV")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");//los cors me habilitan acceder a los endpoints de todas las urls y de cualquier origen.
            }
        };
    }
}
