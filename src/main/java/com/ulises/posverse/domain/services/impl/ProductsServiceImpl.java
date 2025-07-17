package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.domain.model.Product;
import com.ulises.posverse.domain.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    @Override
    public Product saveProduct(final Product product) {
        return null;
    }
}
