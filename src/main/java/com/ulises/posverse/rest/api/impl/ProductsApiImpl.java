package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.rest.api.ProductsApi;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.ProductCreationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProductsApiImpl implements ProductsApi {
    private final ProductsService productsService;
    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<ProductCreationResponseDTO> createProduct(final ProductCreationRequestDTO productCreationRequestDTO) {
        final Product productToSave = this.productMapper.toModel(productCreationRequestDTO);
        final Product savedProduct = this.productsService.saveProduct(productToSave);
        final ProductCreationResponseDTO response = this.productMapper.toDto(savedProduct);
        val uri = URI.create("/products/" + savedProduct.getId());

        return ResponseEntity
                .created(uri)
                .body(response);
    }
}
