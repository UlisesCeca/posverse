package com.ulises.posverse.rest.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulises.posverse.common.objects.CategoryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryCreationResponseDTO extends CategoryBase {
  @NotNull(message = "ID can't be null")
  @Schema(name = "id", example = "Drinks", description = "name of the category", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  private Long id;
}

