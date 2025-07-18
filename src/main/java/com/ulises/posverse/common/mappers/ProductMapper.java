package com.ulises.posverse.common.mappers;

import com.ulises.posverse.common.objects.ProductBase;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.ProductCreationResponseDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductBase toBase(final ProductBase base);

    @InheritConfiguration(name = "toBase")
    Product toModel(final ProductCreationRequestDTO dto);

    @InheritConfiguration(name = "toBase")
    Product toModel(final ProductEntity entity);

    @InheritConfiguration(name = "toBase")
    ProductEntity toEntity(final Product dto);

    @InheritConfiguration(name = "toBase")
    ProductCreationResponseDTO toDto(final Product dto);
}
