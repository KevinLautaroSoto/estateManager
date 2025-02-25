package com.lautaro.spring_boot_microservice_3_api_gateway.security;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder //inicializa todos los campos de esta clase.
public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    transient private String password;//transient se utiliza para que este dato no se serialice y se envie al cliente.
    transient private User user;
    private Set<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
        //return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
        //return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
        //return true;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
        //return true;
    }
}
