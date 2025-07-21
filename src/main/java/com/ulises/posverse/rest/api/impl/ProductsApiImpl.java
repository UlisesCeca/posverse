package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.rest.api.ProductsApi;
import com.ulises.posverse.rest.api.dto.product.create.requests.ProductCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.PagedProductsRetrievalResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductCreationResponseDTO;
import com.ulises.posverse.rest.api.dto.product.create.responses.ProductRetrievalResponseDTO;
import com.ulises.posverse.rest.api.dto.product.params.PagedProductsRetrievalRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
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
        final ProductCreationResponseDTO response = this.productMapper.toCategoryProductCreationDto(savedProduct);
        val uri = URI.create("/products/" + savedProduct.getId());

        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @Override
    public ResponseEntity<ProductRetrievalResponseDTO> getProductById(final Long productId) {
        final Product requestedProduct = this.productsService.findProductById(productId);
        final ProductRetrievalResponseDTO response = this.productMapper.toProductRetrievalDto(requestedProduct);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PagedProductsRetrievalResponseDTO> getPagedProductsList(final PagedProductsRetrievalRequestParam requestParams
    ) {
        final Page<Product> pagedProducts = this.productsService.getPagedProductsList(requestParams);
        final PagedProductsRetrievalResponseDTO response = this.productMapper.toPagedProductsDto(pagedProducts);

        return ResponseEntity.ok(response);
    }
}
