package com.ulises.posverse.rest.api.dto.product.retrieval.filters;

import com.ulises.posverse.common.enums.SortDirection;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

@Data
@ParameterObject
public class PagedProductsRetrievalRequestFilter {
    public static final List<String> VALID_DIRECTIONS = List.of("asc", "desc");

    @Parameter(description = "Page number (default is 1)", example = "1")
    private int page = 0;

    @Parameter(description = "Amount of products per page (default is 10)", example = "10")
    private int size = 20;

    @Parameter(description = "Field used to sort (default is name)", example = "name")
    private String sortBy = "name";

    @Parameter(description = "Sorting direction: asc or desc (default is asc)", example = "asc")
    private SortDirection direction = SortDirection.ASC;

}
