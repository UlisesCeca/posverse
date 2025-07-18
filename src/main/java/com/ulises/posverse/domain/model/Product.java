package com.ulises.posverse.domain.model;

import com.ulises.posverse.common.pojo.StockTrackingBase;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product  {
    private Long id;
    private String name;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private String description;
    private Boolean isCompositeProduct;
    private Boolean isAvailableForSale;
    private StockTrackingBase stockTracking;
    private Category category;
}
