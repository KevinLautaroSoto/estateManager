package com.lautaro.spring_boot_microservice_2_compra.repository;

import com.lautaro.spring_boot_microservice_2_compra.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByUserId(Long userId);
}
