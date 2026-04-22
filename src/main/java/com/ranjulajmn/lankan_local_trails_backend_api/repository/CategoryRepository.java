package com.ranjulajmn.lankan_local_trails_backend_api.repository;

import com.ranjulajmn.lankan_local_trails_backend_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByDeletedAtIsNull();

    Optional<Category> findByIdAndDeletedAtIsNull(Long id);
}
