package com.ulises.posverse.rest.api.impl;

import com.ulises.posverse.domain.services.CategoriesService;
import com.ulises.posverse.rest.api.CategoriesApi;
import com.ulises.posverse.rest.api.dto.requests.CategoryCreationRequestDTO;
import com.ulises.posverse.rest.api.dto.responses.CategoryCreationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoriesApiImpl implements CategoriesApi {
    private final CategoriesService categoriesService;

    @Override
    public ResponseEntity<CategoryCreationResponseDTO> createCategory(final CategoryCreationRequestDTO categoryCreationRequestDTO) {
        this.categoriesService.saveCategory(null);
        return null;
    }
}
