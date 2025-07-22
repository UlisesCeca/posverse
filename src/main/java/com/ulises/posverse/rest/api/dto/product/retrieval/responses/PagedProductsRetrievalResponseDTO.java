package com.ulises.posverse.rest.api.dto.product.retrieval.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-07-18T14:39:42.224865400+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedProductsRetrievalResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "productsList", description = "The retrieved list of products",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("productsList")
    private List<ProductRetrievalResponseDTO> productsList;

    @Schema(name = "totalPages", description = "The total pages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalPages")
    private int totalPages;

    @Schema(name = "totalElements", description = "The total stored products", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalElements")
    private long totalElements;
}
