package com.lautaro.spring_boot_microservice_1_inmueble.repository;

import com.lautaro.spring_boot_microservice_1_inmueble.entities.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepository extends JpaRepository<Estate, Long> {
}
