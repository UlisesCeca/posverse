package com.ulises.posverse.domain.services.impl;

import com.ulises.posverse.common.mappers.CategoryMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.persistence.entities.CategoryEntity;
import com.ulises.posverse.persistence.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category saveCategory(final Category category) {
        final CategoryEntity categoryToSave = this.categoryMapper.toEntity(category);

        this.categoriesRepository.save(categoryToSave);
        category.setId(categoryToSave.getId());

        return category;
    }
}
