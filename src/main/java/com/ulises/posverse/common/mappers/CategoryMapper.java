package com.ulises.posverse.common.mappers;

import com.ulises.posverse.common.objects.CategoryBase;
import com.ulises.posverse.common.objects.ProductBase;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.rest.api.dto.requests.CategoryCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.CategoryCreationResponseDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryBase toBase(final CategoryBase base);

    @InheritConfiguration(name = "toBase")
    Category toModel(final CategoryCreationRequestDTO dto);

    @InheritConfiguration(name = "toBase")
    CategoryEntity toEntity(final Category model);

    @InheritConfiguration(name = "toBase")
    CategoryCreationResponseDTO toDto(final Category model);
}
