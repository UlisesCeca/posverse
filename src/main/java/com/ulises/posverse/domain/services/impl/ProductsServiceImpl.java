package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.product.ProductMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.exceptions.CategoryNotFoundException;
import com.ulises.posverse.exceptions.ProductNotFoundException;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.persistence.repositories.ProductsRepository;
import com.ulises.posverse.rest.api.dto.product.params.PagedProductsRetrievalRequestParam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public Product findProductById(@NonNull final Long productId) {
        final ProductEntity productEntity = this.productsRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return this.productMapper.toModel(productEntity);
    }

    @Override
    public Page<Product> getPagedProductsList(final PagedProductsRetrievalRequestParam requestParams) {
        final Sort sort = requestParams.getDirection().apply(requestParams.getSortBy());
        final PageRequest pageable = PageRequest.of(requestParams.getPage() - 1, requestParams.getSize(), sort);

        return this.productsRepository.findAll(pageable).map(productMapper::toModel);
    }

    @Override
    public void deleteProductById(@NonNull final Long productId) {
        final Product savedProduct = this.findProductById(productId);

        savedProduct.setDeleted(true);
        savedProduct.setDeletedDate(LocalDateTime.now());
        this.productsRepository.save(this.productMapper.toEntity(savedProduct));
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
