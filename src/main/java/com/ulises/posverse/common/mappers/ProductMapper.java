package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.ProductCreationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toModel(final ProductCreationRequestDTO dto);

    ProductEntity toEntity(final Product dto);

    ProductCreationResponseDTO toDto(final Product dto);
}
