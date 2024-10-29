package com.lautaro.spring_boot_microservice_1_inmueble.controller;


import com.lautaro.spring_boot_microservice_1_inmueble.entities.Estate;
import com.lautaro.spring_boot_microservice_1_inmueble.service.EstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/estate")
public class estateController {

    @Autowired
    private EstateService estateService;


    @PostMapping
    public ResponseEntity<?> saveEstate (@RequestBody Estate estate) {
        return new ResponseEntity<>(estateService.saveEstate(estate) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllEstate() {
        return ResponseEntity.ok(estateService.findAllEstate());
    }

    @DeleteMapping("/{estateId}")
    public ResponseEntity<?> deleteEstate(@PathVariable Long estateId) {
        estateService.deleteEstate(estateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
