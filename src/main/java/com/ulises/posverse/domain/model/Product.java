package com.ulises.posverse.domain.model;

import com.ulises.posverse.common.objects.ProductBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Product extends ProductBase {
    private Long id;
    private StockTracking stockTracking;
}
