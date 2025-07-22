package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.product.ProductMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.domain.services.ProductsService;
import com.ulises.posverse.exceptions.CategoryNotFoundException;
import com.ulises.posverse.exceptions.ProductNotFoundException;
import com.ulises.posverse.persistence.entities.ProductEntity;
import com.ulises.posverse.persistence.entities.ProductHistoryEntity;
import com.ulises.posverse.persistence.repositories.ProductsHistoryRepository;
import com.ulises.posverse.persistence.repositories.ProductsRepository;
import com.ulises.posverse.rest.api.dto.product.retrieval.filters.PagedProductsRetrievalRequestFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    private final CategoriesService categoriesService;
    private final ProductsHistoryRepository productsHistoryRepository;
    private final CacheManager cacheManager;

    @Override
    @CachePut(value = "products", key = "#result.id")
    public Product saveProduct(@NonNull final Product product) {
        final ProductEntity productToSave;
        final ProductEntity savedProductEntity;

        this.adjustProductFieldsBeforeStoring(product);
        this.assertProductFieldsExist(product);
        productToSave = this.productMapper.toEntity(product);
        this.productsRepository.save(productToSave);
        this.entityManager.clear();
        savedProductEntity = this.productsRepository.findById(productToSave.getId()).orElse(null);
        final Product savedProductModel = this.productMapper.toModel(savedProductEntity);

        return savedProductModel;
    }

    @Override
    @Cacheable(value = "products", key = "#productId")
    public Product findProductById(@NonNull final Long productId) {
        final ProductEntity productEntity = this.productsRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        final Product savedProductModel = this.productMapper.toModel(productEntity);

        return savedProductModel;
    }

    @Override
    @Cacheable(value = "productsPageCache", key = "#requestParams.page + '-' + #requestParams.size + '-' + " +
            "#requestParams.sortBy + '-' + #requestParams.direction")
    public Page<Product> getPagedProductsList(@NonNull final PagedProductsRetrievalRequestFilter requestParams) {
        final Sort sort = requestParams.getDirection().apply(requestParams.getSortBy());
        final PageRequest pageable = PageRequest.of(requestParams.getPage() - 1, requestParams.getSize(), sort);

        return this.productsRepository.findAll(pageable).map(productMapper::toModel);
    }

    @Override
    @CacheEvict(value = "products", key = "#productId")
    public void deleteProductById(@NonNull final Long productId) {
        final Product savedProduct = this.findProductById(productId);

        savedProduct.setDeleted(true);
        savedProduct.setDeletedDate(LocalDateTime.now());

        final ProductEntity deletedProduct = this.productMapper.toEntity(savedProduct);
        this.saveProductHistory(deletedProduct);
        this.productsRepository.save(deletedProduct);
    }

    @Override
    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(@NonNull final Product product) {
        this.findProductById(product.getId());
        return this.saveProduct(product);
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

    private void adjustProductFieldsBeforeStoring(@NonNull final Product product) {
        this.adjustProductStockTracking(product);
        product.setSaleProfit(this.calculateSaleProfit(product));
    }

    private void adjustProductStockTracking(@NonNull final Product product) {
        if (!product.getStockTracking().getMustTrackStock()) {
            product.getStockTracking().setStockAmount(null);
            product.getStockTracking().setLowStockWarning(null);
        }
    }

    private BigDecimal calculateSaleProfit(@NonNull final Product product) {
        final BigDecimal saleProfit;

        if (product.getSalePrice() == null || product.getPurchasePrice() == null) {
            saleProfit = null;
        } else {
            saleProfit = product.getSalePrice().subtract(product.getPurchasePrice());
        }

        return saleProfit;
    }

    private void saveProductHistory(@NonNull final ProductEntity productEntity) {
        final ProductHistoryEntity productHistory = this.productMapper.toProductHistory(productEntity);

        this.productsHistoryRepository.save(productHistory);
    }
}
