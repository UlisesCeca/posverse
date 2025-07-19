package com.ulises.posverse.persistence.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
public class StockTrackingEntity {
    private Boolean mustTrackStock;
    private BigDecimal stockAmount;
    private BigDecimal lowStockWarning;
}
