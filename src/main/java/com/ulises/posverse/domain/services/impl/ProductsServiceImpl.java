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
import com.ulises.posverse.persistence.specifications.DynamicSpecificationBuilder;
import com.ulises.posverse.rest.api.dto.product.retrieval.filters.PagedProductsRetrievalFilters;
import com.ulises.posverse.rest.api.dto.product.retrieval.filters.PagedProductsRetrievalPagingParam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    @CachePut(value = "products", key = "#result.id")
    @CacheEvict(value = "productsPageCache", allEntries = true)
    public Product saveProduct(@NonNull final Product product) {
        final ProductEntity productToSave;
        final ProductEntity savedProductEntity;

        this.adjustProductFieldsBeforeStoring(product);
        this.assertProductFieldsExist(product);
        productToSave = this.productMapper.toEntity(product);
        this.productsRepository.save(productToSave);
        this.entityManager.clear();
        savedProductEntity = this.productsRepository.findById(productToSave.getId()).orElse(null);

        return this.productMapper.toModel(savedProductEntity);
    }

    @Override
    @Cacheable(value = "products", key = "#productId")
    public Product findProductById(@NonNull final Long productId) {
        final ProductEntity productEntity = this.productsRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return this.productMapper.toModel(productEntity);
    }

    @Override
    @Cacheable(value = "productsPageCache", keyGenerator = "pagedProductsKeyGenerator")
    public Page<Product> getPagedProductsList(@NonNull final PagedProductsRetrievalPagingParam pagingParams,
                                              @NonNull final PagedProductsRetrievalFilters filters) {
        final Sort sort = pagingParams.getDirection().apply(pagingParams.getSortBy());
        final PageRequest pageable = PageRequest.of(pagingParams.getPage() - 1, pagingParams.getSize(), sort);
        final DynamicSpecificationBuilder<ProductEntity> builder = new DynamicSpecificationBuilder<>();
        final Specification<ProductEntity> spec = builder.buildFromFilter(filters);

        return this.productsRepository.findAll(spec, pageable).map(productMapper::toModel);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "products", key = "#productId"),
            @CacheEvict(value = "productsPageCache", allEntries = true)
    })
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
    @CacheEvict(value = "productsPageCache", allEntries = true)
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
        product.setDeleted(false);
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
