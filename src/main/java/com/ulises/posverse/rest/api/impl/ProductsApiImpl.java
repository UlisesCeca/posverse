package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.rest.api.ProductsApi;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsApiImpl implements ProductsApi {
    private final ProductsService productsService;
    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<Product> createProduct(final ProductCreationRequestDTO productCreationRequestDTO) {
        val product = this.productMapper.toModel(productCreationRequestDTO);
        val savedProduct = this.productsService.saveProduct(product);

        return ResponseEntity.ok(savedProduct);
    }
}
