package com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.impl;

import com.lautaro.spring_boot_microservice_3_api_gateway.security.UserPrincipal;
import com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.JwtProvider;
import com.lautaro.spring_boot_microservice_3_api_gateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    //Clave secreta para firmar el token JWT, leida desde application.properties
    @Value("&{app.jwt.secret}")
    private String JWT_SECRET;

    //Tiempo de expiración del token JWT.
    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth) {
        //Convierte las autoridades del usuario en un String separado por comas.
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //Genera una clave HMAC utilizando la clave secreta que configure.
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        //Crear un mapa para almacenar los claims (información) para el JWT.
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", auth.getUsername());//Define el sujeto del token (nombre de usuario).
        claims.put("exp", new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS));//Define la fecha de expiracion del token.
        claims.put("roles", authorities);//Agrega los roles como un claim.
        claims.put("userId", auth.getId());//Agrega el id del usuario.

        //Construye el token JWT con los claims, la clave y el algoritmo de firma
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();//Genera el token en formato completo.
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {//Extrae los claims del token presente en la solicitud Http
        Claims claims = extractClaims(request);
        if (claims == null) {
            return null;//si no hay claims no se puede autenticar, por lo que se retorna null.
        }

        //Obtiene el nombre e id del usuario de los claims.
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        //Obtiene los roles de los claims y los convierte en un conjunto de GrantedAuthority
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        //Crea un objeto userDetail con la info el usuario.
        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .id(userId)
                .build();

        //Si no hay nombre de usuario no se puede autenticar.
        if (username == null) {
            return null;
        }

        //Crea y retorna un objeto UsernamePasswordAuthenticationToken que representa la autorización del usuario.
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if (claims == null) {
            return false;
        }

        if (claims.getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    //Los claims son las propiedades de los valores que están en el interior del token.
    private Claims extractClaims(HttpServletRequest request) {
        //extrae el token JWT de la solicitu Http
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if (token == null) {
            return null;
        }

        //Genera la clave para firmar el token utilizando el secreto configurado.
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        //Crea un objeto JwtParser para analizar el token
        return Jwts.parser()
                // Configura el JwtParser para que se verifique la firma del token utilizando la clave secreta "key"
                .verifyWith(key)
                // Construye el JwtParser
                .build()
                // Analiza el token y extrae los claims, asegurándose de que el token esté firmando
                .parseSignedClaims(token)
                // Obtiene los claims (payload) del token analizado
                .getPayload();
    }
}
