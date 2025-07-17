package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.persistence.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;

    @Override
    public Product saveProduct(final Product product) {
        val productEntity = this.productMapper.toEntity(product);

        this.productsRepository.save(productEntity);
        product.setId(productEntity.getId());

        return product;
    }
}
