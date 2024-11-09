    package com.lautaro.spring_boot_microservice_3_api_gateway.service.impl;

    import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
    import com.lautaro.spring_boot_microservice_3_api_gateway.security.UserPrincipal;
    import com.lautaro.spring_boot_microservice_3_api_gateway.security.jwt.JwtProvider;
    import com.lautaro.spring_boot_microservice_3_api_gateway.service.AuthenticationService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.stereotype.Service;

    @Service
    public class AuthenticationServiceImpl implements AuthenticationService {

        @Autowired
        private AuthenticationManager authenticationManager;//para autenticar al usuario

        @Autowired
        private JwtProvider jwtProvider;//Genera tokens jwt

        @Override
        public User signInAndReturnJWT(User signInRequest) { //Método que realiza la autenticación del usuario y devuelve un token Jwt.
            try {

                System.out.println("Autenticando usuario: " + signInRequest.getUsername()); //Imprimir credenciales de entrada para verificación.

                //Crea un objeto UsernamePasswordAuthenticationToken con el nombre de usuario y contraseña.
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
                ); //Autentica el usuario con el authenticationManager

                Object principal = authentication.getPrincipal();
                if (!(principal instanceof UserPrincipal userPrincipal)) {
                    throw new RuntimeException("Error: El objeto principal no es de tipo UserPrincipal");
                }

                //obtiene el UserPrincipal del objeto Authentication

                //Genera un token jwt para el usuario utilizando el jwtProvider.
                String jwt = jwtProvider.generateToken(userPrincipal);

                //Obtiene le objeto User del UserPrincipal
                User signInUser = userPrincipal.getUser();

                //Establece el token Jwt en el objeto User
                signInUser.setToken(jwt);

                System.out.println("Token generado exitosamente: " + jwt);//Verifica el token generado.

                //Devuelve el objeto User con el token JWT
                return signInUser;
            } catch (Exception e) {
                System.err.println("Error durante la autenticación: " + e.getMessage());
                throw new RuntimeException("Error de autenticación: " + e.getMessage());
            }
        }
    }
