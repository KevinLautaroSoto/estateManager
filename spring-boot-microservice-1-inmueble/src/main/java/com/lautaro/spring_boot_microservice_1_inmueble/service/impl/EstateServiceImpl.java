package com.lautaro.spring_boot_microservice_1_inmueble.service.impl;

import com.lautaro.spring_boot_microservice_1_inmueble.entities.Estate;
import com.lautaro.spring_boot_microservice_1_inmueble.repository.EstateRepository;
import com.lautaro.spring_boot_microservice_1_inmueble.service.EstateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstateServiceImpl implements EstateService {


    private final EstateRepository estateRepository;

    public EstateServiceImpl(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    @Override
    public Estate saveEstate(Estate estate) {
        estate.setCreationDate(LocalDateTime.now()); //set creation date now.
        return estateRepository.save(estate);
    }

    @Override
    public void deleteEstate(Long estateId) {
        estateRepository.deleteById(estateId);
    }

    @Override
    public List<Estate> findAllEstate() {
        return estateRepository.findAll();
    }
}
