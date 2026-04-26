package com.ranjulajmn.lankan_local_trails_backend_api.repository;

import com.ranjulajmn.lankan_local_trails_backend_api.entity.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceCategoryRepository extends JpaRepository<PlaceCategory, Long> {
    List<PlaceCategory> findByPlaceId(Long placeId);
    void deleteByPlaceId(Long placeId);

    List<PlaceCategory> findByCategoryId(Long categoryId);

}
