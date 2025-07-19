package com.ulises.posverse.rest.api.dto.product.create.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-18T14:39:42.224865400+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
public class CategoryProductCreationRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", example = "2", description = "ID of the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    private Long id;
}
