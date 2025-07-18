package com.ulises.posverse.rest.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.annotation.Generated;

/**
 * ProductCreationResponseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-18T01:48:09.427530900+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
public class ProductCreationResponseDTO {
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  private BigDecimal id;

  @Schema(name = "name", example = "Coke", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  private String name;
}

