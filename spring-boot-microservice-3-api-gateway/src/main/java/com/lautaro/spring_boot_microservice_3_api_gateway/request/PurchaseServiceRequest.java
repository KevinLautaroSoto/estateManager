package com.lautaro.spring_boot_microservice_3_api_gateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        value = "compra-service",
        path = "api/v1/purchase",
        //url = "${compras.service.url}", ya no necesito pasarle url desde application.properties. porque con el value compra-service
        //eureka ya "sabe" a que microservicio debe conectares este microservicio y lo hace por nosotros
        configuration = FeignConfiguration.class
        )
public interface PurchaseServiceRequest {

    @PostMapping
    Object savePurchase(@RequestBody Object requestBody);

    @GetMapping("{userId}")
    List<Object> getAllPurchaseOfUser(@PathVariable("userId") Long userId);
}
