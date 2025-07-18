package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.rest.api.dto.requests.CategoryCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.CategoryCreationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toModel(final CategoryCreationRequestDTO dto);

    CategoryEntity toEntity(final Category model);

    CategoryCreationResponseDTO toDto(final Category model);
}
