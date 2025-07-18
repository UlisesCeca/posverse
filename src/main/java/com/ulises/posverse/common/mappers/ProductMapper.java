package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.product.create.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductCreationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toModel(final ProductCreationRequestDTO dto);

    Product toModel(final ProductEntity entity);

    ProductEntity toEntity(final Product dto);

    ProductCreationResponseDTO toDto(final Product dto);

    CategoryProductCreationResponseDTO toDto(final Category model);
}
