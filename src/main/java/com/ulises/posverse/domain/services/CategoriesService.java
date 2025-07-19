package com.ulises.posverse.domain.services;

import com.ulises.posverse.domain.model.Category;

import java.util.Optional;

public interface CategoriesService {
    Category saveCategory(final Category category);

    Optional<Category> findById(final Long id);
}
