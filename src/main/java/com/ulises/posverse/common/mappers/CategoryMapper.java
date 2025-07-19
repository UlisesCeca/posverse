package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.rest.api.dto.category.create.requests.CategoryCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.category.create.responses.CategoryCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductCreationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toModel(final CategoryCreationRequestDTO dto);

    CategoryEntity toEntity(final Category model);

    CategoryCreationResponseDTO toDto(final Category model);

    CategoryProductCreationResponseDTO map(Category value);

    Category toModel(final CategoryEntity categoryEntity);
}
