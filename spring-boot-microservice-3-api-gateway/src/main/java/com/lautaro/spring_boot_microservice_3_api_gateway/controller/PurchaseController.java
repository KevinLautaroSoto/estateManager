package com.lautaro.spring_boot_microservice_3_api_gateway.controller;

import com.lautaro.spring_boot_microservice_3_api_gateway.request.PurchaseServiceRequest;
import com.lautaro.spring_boot_microservice_3_api_gateway.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseServiceRequest purchaseServiceRequest;

    @PostMapping
    public ResponseEntity<?> savePurchase(@RequestBody Object purchase) {
        return new ResponseEntity<>(purchaseServiceRequest.savePurchase(purchase), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPurchaseOfUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {//el AuthenticationPrincipal representa al usuario que inicio sesion.
       return ResponseEntity.ok(purchaseServiceRequest.getAllPurchaseOfUser(userPrincipal.getId()));
    }
}
