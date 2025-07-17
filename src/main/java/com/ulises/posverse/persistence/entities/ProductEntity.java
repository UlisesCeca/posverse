package com.ulises.posverse.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCTS")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremental
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
