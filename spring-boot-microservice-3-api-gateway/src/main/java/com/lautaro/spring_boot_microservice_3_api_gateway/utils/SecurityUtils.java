package com.lautaro.spring_boot_microservice_3_api_gateway.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUtils {

    public static final String ROLE_PREFIX = "ROLE_";//todos los roles con los que trabaje dentro del modelo de usuarios
    //siempre deben tener la palabra ROLE_ ej: ROLE_admin.

    public static SimpleGrantedAuthority convertToAuthority(String role) {
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role; //en el caso que ya se este utilizando el prefijo ROLE_ no hace nada y si no se estaba utilizando se lo agrega.
        return new SimpleGrantedAuthority(formattedRole);
    }
}
