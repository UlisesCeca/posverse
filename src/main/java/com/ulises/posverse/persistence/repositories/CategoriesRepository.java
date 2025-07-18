package com.ulises.posverse.persistence.repositories;

import com.ulises.posverse.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository  extends JpaRepository<CategoryEntity, Long> {
}