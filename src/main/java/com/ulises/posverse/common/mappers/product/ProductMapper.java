package com.ulises.posverse.common.mappers.product;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.product.create.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.PagedProductsRetrievalResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductRetrievalResponseDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toModel(final ProductCreationRequestDTO dto);

    Product toModel(final ProductEntity entity);

    ProductEntity toEntity(final Product dto);

    ProductCreationResponseDTO toCategoryProductCreationDto(final Product dto);

    CategoryProductCreationResponseDTO toCategoryProductCreationDto(final Category model);

    ProductRetrievalResponseDTO toProductRetrievalDto(final Product model);

    @Mapping(target = "productsList", source = "content")
    PagedProductsRetrievalResponseDTO toPagedProductsDto(final Page<Product> pagedProducts);

    @BeforeMapping
    default void setStockValues(final ProductCreationRequestDTO dto) {
        if (!dto.getStockTracking().getMustTrackStock()) {
            dto.getStockTracking().setStockAmount(null);
            dto.getStockTracking().setLowStockWarning(null);
        }
    }

    @BeforeMapping
    default void setStockValues(final Product model) {
        if (!model.getStockTracking().getMustTrackStock()) {
            model.getStockTracking().setStockAmount(null);
            model.getStockTracking().setLowStockWarning(null);
        }
    }
}
