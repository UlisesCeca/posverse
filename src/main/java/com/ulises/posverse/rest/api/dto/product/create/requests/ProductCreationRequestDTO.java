package com.ulises.posverse.rest.api.dto.product.create.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulises.posverse.common.pojo.StockTrackingBase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-17T23:57:38.201939200+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
public class ProductCreationRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "name can't be empty")
    @Schema(name = "name", example = "Coke", description = "The name of the product", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("name")
    private String name;

    @Digits(integer =  Integer.MAX_VALUE, fraction = 2)
    @Schema(name = "salePrice", example = "2.50", description = "The sale price",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("salePrice")
    private BigDecimal salePrice;

    @Digits(integer =  Integer.MAX_VALUE, fraction = 2)
    @Schema(name = "purchasePrice", example = "0.70", description = "The purchase price",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("purchasePrice")
    private BigDecimal purchasePrice;

    @Schema(name = "description", example = "Refreshing drink", description = "The description of the product",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("description")
    private String description;

    @Schema(name = "isCompositeProduct", example = "true",
            description = "If the product is composed by one or more different products",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("isCompositeProduct")
    private Boolean isCompositeProduct;

    @Schema(name = "isAvailableForSale", example = "true", description = "If the product is available for sale or not",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("isAvailableForSale")
    private Boolean isAvailableForSale;

    @Valid
    @NotNull(message = "stockTracking can't be null")
    @Schema(name = "stockTracking", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Information related to the stock")
    @JsonProperty("stockTracking")
    private StockTrackingBase stockTracking;

    @Valid
    @Schema(name = "category", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Category associated to the product")
    @JsonProperty("category")
    private CategoryProductCreationRequestDTO category;
}

