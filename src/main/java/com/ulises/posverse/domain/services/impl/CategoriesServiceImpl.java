package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.CategoryMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.persistence.repositories.CategoriesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category saveCategory(@NonNull final Category category) {
        final CategoryEntity categoryToSave = this.categoryMapper.toEntity(category);

        this.categoriesRepository.save(categoryToSave);
        category.setId(categoryToSave.getId());

        return category;
    }

    @Override
    public Optional<Category> findById(@NonNull final Long id) {
        return categoriesRepository.findById(id)
                .map(categoryMapper::toModel);
    }
}
