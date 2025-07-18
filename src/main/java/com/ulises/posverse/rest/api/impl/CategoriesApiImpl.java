package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.common.mappers.CategoryMapper;
import com.ulises.posverse.domain.model.Category;
import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.rest.api.CategoriesApi;
import com.ulises.posverse.rest.api.dto.requests.CategoryCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.CategoryCreationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CategoriesApiImpl implements CategoriesApi {
    private final CategoriesService categoriesService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryCreationResponseDTO> createCategory(final CategoryCreationRequestDTO categoryCreationRequestDTO) {
        final Category categoryToSave = this.categoryMapper.toModel(categoryCreationRequestDTO);
        final Category savedCategory = this.categoriesService.saveCategory(categoryToSave);
        final CategoryCreationResponseDTO response = this.categoryMapper.toDto(savedCategory);
        val uri = URI.create("/products/" + savedCategory.getId());

        return ResponseEntity
                .created(uri)
                .body(response);
    }
}
