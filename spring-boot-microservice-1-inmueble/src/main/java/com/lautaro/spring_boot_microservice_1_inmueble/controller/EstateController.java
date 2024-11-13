package com.lautaro.spring_boot_microservice_1_inmueble.controller;


import com.lautaro.spring_boot_microservice_1_inmueble.entities.Estate;
import com.lautaro.spring_boot_microservice_1_inmueble.service.EstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/estate")
public class EstateController {

    private static final Logger log = LoggerFactory.getLogger(EstateController.class);
    @Autowired
    private EstateService estateService;


    @PostMapping
    public ResponseEntity<?> saveEstate (@RequestBody Estate estate) {
        try {
            return new ResponseEntity<>(estateService.saveEstate(estate) , HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("ControllerEstate: Error al guardar el inmueble.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el inmueble: " + e.getMessage());
        }
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
