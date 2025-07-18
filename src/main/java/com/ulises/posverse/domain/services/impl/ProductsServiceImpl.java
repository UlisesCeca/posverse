package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.persistence.repositories.ProductsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Product saveProduct(final Product product) {
        final ProductEntity productToSave = this.productMapper.toEntity(product);
        final ProductEntity savedProductEntity;

        this.productsRepository.save(productToSave);
        this.entityManager.clear();
        savedProductEntity = this.productsRepository.findById(productToSave.getId()).orElse(null);

        return this.productMapper.toModel(savedProductEntity);
    }
}
