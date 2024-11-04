package com.lautaro.spring_boot_microservice_3_api_gateway.controller;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
import com.lautaro.spring_boot_microservice_3_api_gateway.service.AuthenticationService;
import com.lautaro.spring_boot_microservice_3_api_gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); //porque estoy intentando ingresar un nuevo usuario que ya existe en la base de datos
        }

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);

    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }
}
