package com.ulises.posverse.rest.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import com.ulises.posverse.common.objects.ProductBase;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductCreationResponseDTO extends ProductBase {
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  private Long id;
}