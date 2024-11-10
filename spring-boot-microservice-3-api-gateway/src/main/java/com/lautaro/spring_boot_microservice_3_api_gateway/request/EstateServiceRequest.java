package com.lautaro.spring_boot_microservice_3_api_gateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "inmueble-service",
        path =  "/api/v1/estate",
        url = "${inmueble.service.url}",
        configuration = FeignConfiguration.class
)
public interface EstateServiceRequest {

    @PostMapping
    Object saveEstate(@RequestBody Object requestBody);

    @DeleteMapping("/{estateId}")
    void deleteEstate(@PathVariable("inmuevleId") Long estateId);

    @GetMapping
    List<Object> getAllEstates();

}
