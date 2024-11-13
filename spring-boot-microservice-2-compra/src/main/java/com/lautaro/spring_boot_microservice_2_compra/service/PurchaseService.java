package com.lautaro.spring_boot_microservice_2_compra.service;

import com.lautaro.spring_boot_microservice_2_compra.entities.Purchase;

import java.util.List;

public interface PurchaseService {
    Purchase savePurchase(Purchase purchase);

    List<Purchase> finaAllComprasOfUser(Long userId);
}
