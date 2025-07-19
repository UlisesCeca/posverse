package com.ulises.posverse.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockTracking  {
    private Boolean mustTrackStock;
    private BigDecimal stockAmount;
    private BigDecimal lowStockWarning;
}
