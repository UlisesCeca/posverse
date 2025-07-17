package com.ulises.posverse.common.mappers;

import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toModel(final ProductCreationRequestDTO dto);
}
