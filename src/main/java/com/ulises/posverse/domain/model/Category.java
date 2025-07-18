package com.ulises.posverse.domain.model;

import com.ulises.posverse.common.objects.CategoryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends CategoryBase {
    private Long id;
}
