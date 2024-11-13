package com.lautaro.spring_boot_microservice_1_inmueble.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="inmueble")
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length = 150, nullable = false)
    private String name;

    @Column(name="address", length = 500, nullable = false)
    private String address;

    @Column(name = "picture", length = 1200, nullable = true)
    private String picture;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="creation_date", nullable = false)
    private LocalDateTime creationDate;


}
