package com.ulises.posverse.domain.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StockTracking implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean mustTrackStock;
    private BigDecimal stockAmount;
    private BigDecimal lowStockWarning;
}
