package com.ulises.posverse.rest.api.dto.product.retrieval.filters;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
public class PagedProductsRetrievalFilters {

    @Parameter(description = "If the product exists or not (default is false)", example = "false")
    public boolean deleted = false;

    @Parameter(description = "Name of the product)", example = "Coke")
    public String name;

}
