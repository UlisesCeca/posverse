package com.ulises.posverse.domain.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private String description;
    private Boolean isCompositeProduct;
    private Boolean isAvailableForSale;
    private StockTracking stockTracking;
    private Category category;
    private Boolean deleted;
    private LocalDateTime deletedDate;
    private BigDecimal saleProfit;
}
