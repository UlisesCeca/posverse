package com.ulises.posverse.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "SALE_PRICE")
    private BigDecimal salePrice;

    @Column(name = "PURCHASE_PRICE")
    private BigDecimal purchasePrice;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_COMPOSITE_PRODUCT")
    private Boolean isCompositeProduct;

    @Column(name = "IS_AVAILABLE_FOR_SALE")
    private Boolean isAvailableForSale;

    @Embedded
    private StockTrackingEntity stockTracking;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY")
    private CategoryEntity category;
}
