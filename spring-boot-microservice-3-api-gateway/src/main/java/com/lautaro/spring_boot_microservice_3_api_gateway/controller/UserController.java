package com.lautaro.spring_boot_microservice_3_api_gateway.controller;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.Role;
import com.lautaro.spring_boot_microservice_3_api_gateway.security.UserPrincipal;
import com.lautaro.spring_boot_microservice_3_api_gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Esto solo debe ser posible para usuarios que ya esten logeados en la app, por lo que necesito el AuthenticationPrincipal.
    //También tengo que tener en cuenta el token de seguridad que se ha generado al logear.
    @PutMapping("/change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role) {
        userService.changeRole(role, userPrincipal.getUsername());

        return ResponseEntity.ok(true);
    }
}
