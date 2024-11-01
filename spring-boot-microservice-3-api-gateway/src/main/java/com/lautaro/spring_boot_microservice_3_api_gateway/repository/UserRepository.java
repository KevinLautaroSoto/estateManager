package com.lautaro.spring_boot_microservice_3_api_gateway.repository;

import com.lautaro.spring_boot_microservice_3_api_gateway.entities.Role;
import com.lautaro.spring_boot_microservice_3_api_gateway.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    @Modifying //como se trata de un metodo que astualiza data en una tabla de la base de datos se pone esto.
    @Query("update User set role=:role where username=:username")//sentencia SQL
    void updateUserRole (@Param("username") String username, @Param("role") Role role);
}
