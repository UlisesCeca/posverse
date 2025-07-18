package com.ulises.posverse.persistence.entities;

import com.ulises.posverse.common.objects.StockTrackingBase;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
public class StockTrackingEntity extends StockTrackingBase {
}
