package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.product.create.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductCreationResponseDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toModel(final ProductCreationRequestDTO dto);

    Product toModel(final ProductEntity entity);

    ProductEntity toEntity(final Product dto);

    ProductCreationResponseDTO toDto(final Product dto);

    CategoryProductCreationResponseDTO toDto(final Category model);

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
