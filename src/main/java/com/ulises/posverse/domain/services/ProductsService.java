package com.ulises.posverse.domain.services;

import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.rest.api.dto.product.retrieval.filters.PagedProductsRetrievalFilters;
import com.ulises.posverse.rest.api.dto.product.retrieval.filters.PagedProductsRetrievalPagingParam;
import org.springframework.data.domain.Page;

public interface ProductsService {
    Product saveProduct(final Product product);

    Product findProductById(final Long productId);

    Page<Product> getPagedProductsList(final PagedProductsRetrievalPagingParam requestParams,
                                       final PagedProductsRetrievalFilters filters);

    void deleteProductById(final Long productId);

    Product updateProduct(final Product product);
}
