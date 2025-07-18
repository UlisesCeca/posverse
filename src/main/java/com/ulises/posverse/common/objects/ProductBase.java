package com.ulises.posverse.common.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ProductCreationRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-17T23:57:38.201939200+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductBase implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "name can't be empty")
  @Schema(name = "name", example = "Coke", requiredMode = Schema.RequiredMode.REQUIRED)
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
          requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isCompositeProduct")
  private Boolean isCompositeProduct;

  @Schema(name = "isAvailableForSale", example = "true", description = "If the product is available for sale or not",
          requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isAvailableForSale")
  private Boolean isAvailableForSale;

  @Schema(name = "mustTrackStock", description = "If the product should track the inventory such as the stock",
          requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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

