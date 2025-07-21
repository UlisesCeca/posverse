package com.ulises.posverse.domain.services;

import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.rest.api.dto.product.params.PagedProductsRetrievalRequestParam;
import org.springframework.data.domain.Page;

public interface ProductsService {
    Product saveProduct(final Product product);

    Product findProductById(final Long id);

    Page<Product> getPagedProductsList(final PagedProductsRetrievalRequestParam requestParams);
}
