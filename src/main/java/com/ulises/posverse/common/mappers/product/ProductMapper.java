package com.ulises.posverse.common.mappers.product;

import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.rest.api.dto.product.create.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.CategoryProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.retrieval.responses.PagedProductsRetrievalResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.retrieval.responses.ProductRetrievalResponseDTO;
import com.ulises.posverse.rest.api.dto.product.update.requests.ProductUpdateRequestDTO;
import com.ulises.posverse.rest.api.dto.product.update.responses.ProductUpdateResponseDTO;
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

    Product toModel(final ProductUpdateRequestDTO dto);

    ProductUpdateResponseDTO toUpdateResponseDto(final Product model);

}
