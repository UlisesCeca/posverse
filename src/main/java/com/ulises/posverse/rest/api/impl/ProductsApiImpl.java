package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.rest.api.ProductsApi;
import com.ulises.posverse.rest.api.dto.requests.ProductCreationRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsApiImpl implements ProductsApi {
    private final ProductsService productsService;

    @Override
    public ResponseEntity<Void> createProduct(final ProductCreationRequestDTO productCreationRequestDTO) {
        this.productsService.createProduct(null);
        return ResponseEntity.noContent().build();
    }
}
