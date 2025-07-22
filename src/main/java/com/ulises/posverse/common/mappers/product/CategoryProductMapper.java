package com.ulises.posverse.common.mappers.product;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.rest.api.dto.product.create.requests.CategoryProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductRetrievalResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryProductMapper {

    CategoryProductRetrievalResponseDTO toCategoryProductRetrievalDto(final Category model);

    Category toCategoryProductModel(final CategoryProductCreationRequestDTO dto);

    Category toCategoryProductModel(final CategoryEntity entity);

    CategoryEntity toCategoryProductEntity(final Category model);
}
