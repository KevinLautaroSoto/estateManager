package com.lautaro.spring_boot_microservice_3_api_gateway.controller;

import com.lautaro.spring_boot_microservice_3_api_gateway.request.EstateServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/estate")
public class EstateController {


    @Autowired
    private EstateServiceRequest estateServiceRequest;

    @PostMapping
    public ResponseEntity<?> saveEstate(@RequestBody Object estate) {
        return new ResponseEntity<>(estateServiceRequest.saveEstate(estate), HttpStatus.CREATED);
    }

    @DeleteMapping("{estateId}")
    public ResponseEntity<?> deleteEstate(@PathVariable("estateId")Long estateId) {
        estateServiceRequest.deleteEstate(estateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllEstates() {
        return ResponseEntity.ok(estateServiceRequest.getAllEstates());
    }

}
