package com.ulises.posverse.rest.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.annotation.Generated;

/**
 * ProductCreationRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-17T23:57:38.201939200+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
public class ProductCreationRequestDTO {
  @NotEmpty(message = "name can't be empty")
  @Schema(name = "name", example = "Coke", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  private String name;
}

