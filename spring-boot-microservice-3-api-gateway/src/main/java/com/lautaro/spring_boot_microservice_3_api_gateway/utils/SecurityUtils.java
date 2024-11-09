package com.lautaro.spring_boot_microservice_3_api_gateway.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

public class SecurityUtils {

    public static final String ROLE_PREFIX = "ROLE_";//todos los roles con los que trabaje dentro del modelo de usuarios
    //siempre deben tener la palabra ROLE_ ej: ROLE_admin.

    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_TYPE = "Bearer";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE + " "; //concatena un espacio en blanco para luego agregar el token, para cumplir con el formato correcto.

    public static SimpleGrantedAuthority convertToAuthority(String role) {
        String formattedRole = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role; //en el caso que ya se este utilizando el prefijo ROLE_ no hace nada y si no se estaba utilizando se lo agrega.
        return new SimpleGrantedAuthority(formattedRole);
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) { //Si existe el bearerToken dentro del header del request y si existe, este debe comenzar con la palabra Bearer:
            return bearerToken.substring(7); //para que retorne el bearer token si el "Bearer " solo el valor.
        }
        return null;
    }
}
