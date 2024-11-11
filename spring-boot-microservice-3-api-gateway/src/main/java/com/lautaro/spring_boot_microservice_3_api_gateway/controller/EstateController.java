package com.lautaro.spring_boot_microservice_3_api_gateway.controller;

import com.lautaro.spring_boot_microservice_3_api_gateway.request.EstateServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/estate")
public class EstateController {


    private static final Logger log = LoggerFactory.getLogger(EstateController.class);
    @Autowired
    private EstateServiceRequest estateServiceRequest;

    @PostMapping
    public ResponseEntity<?> saveEstate(@RequestBody Object estate) {
        try {
            return new ResponseEntity<>(estateServiceRequest.saveEstate(estate), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al guardar el inmueble, ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el inmueble: " + e.getMessage());
        }
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
