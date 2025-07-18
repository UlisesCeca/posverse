package com.ulises.posverse.common.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-18T14:39:42.224865400+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
public class CategoryBase {
    @NotNull(message = "name can't be empty")
    @Schema(name = "name", example = "Drinks", description = "name of the category", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("name")
    private String name;
}
