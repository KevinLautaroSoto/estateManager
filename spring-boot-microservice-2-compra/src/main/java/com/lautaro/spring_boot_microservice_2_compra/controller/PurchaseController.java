package com.lautaro.spring_boot_microservice_2_compra.controller;


import com.lautaro.spring_boot_microservice_2_compra.entities.Purchase;
import com.lautaro.spring_boot_microservice_2_compra.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> savePurchase(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(
                purchaseService.savePurchase(purchase),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllPurchaseOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.finaAllComprasOfUser(userId));
    }
}
