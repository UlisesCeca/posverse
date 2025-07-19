package com.ulises.posverse.rest.api.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-18T13:48:25.565547900+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockTrackingDTO {
    @NotNull(message = "mustTrackStock can't be null")
    @Schema(name = "mustTrackStock", description = "If the product should track the inventory such as the stock",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("mustTrackStock")
    private Boolean mustTrackStock;

    @Schema(name = "stockAmount", example = "102", description = "The available amount of stock of the product",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("stockAmount")
    private BigDecimal stockAmount;

    @Schema(name = "lowStockWarning", example = "24",
            description = "The amount at which the user should be warned when the stock amount is equal or lower to",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("lowStockWarning")
    private BigDecimal lowStockWarning;
}