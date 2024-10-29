package com.lautaro.spring_boot_microservice_1_inmueble.service;

import com.lautaro.spring_boot_microservice_1_inmueble.entities.Estate;

import java.util.List;

public interface EstateService {
    Estate saveEstate(Estate estate);

    void deleteEstate(Long estateId);

    List<Estate> findAllEstate();
}
