package com.lautaro.spring_boot_microservice_2_compra.service.impl;

import com.lautaro.spring_boot_microservice_2_compra.entities.Purchase;
import com.lautaro.spring_boot_microservice_2_compra.repository.PurchaseRepository;
import com.lautaro.spring_boot_microservice_2_compra.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Purchase savePurchase(Purchase purchase) {
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> finaAllComprasOfUser(Long userId) {
        return purchaseRepository.findAllByUserId(userId);
    }
}
