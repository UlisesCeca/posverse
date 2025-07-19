package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.ProductMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.exceptions.CategoryNotFoundException;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.persistence.repositories.ProductsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    private final CategoriesService categoriesService;

    @Override
    public Product saveProduct(@NonNull final Product product) {
        final ProductEntity productToSave;
        final ProductEntity savedProductEntity;

        this.assertProductFieldsExist(product);
        productToSave = this.productMapper.toEntity(product);
        this.productsRepository.save(productToSave);
        this.entityManager.clear();
        savedProductEntity = this.productsRepository.findById(productToSave.getId()).orElse(null);

        return this.productMapper.toModel(savedProductEntity);
    }

    private void assertProductFieldsExist(@NonNull final Product product) {
        this.assertProductCategoryExists(product.getCategory());
    }

    private void assertProductCategoryExists(final Category category) {
        if (category != null) {
            this.categoriesService.findById(category.getId())
                    .orElseThrow(() -> new CategoryNotFoundException(category.getId()));
        }
    }
}
