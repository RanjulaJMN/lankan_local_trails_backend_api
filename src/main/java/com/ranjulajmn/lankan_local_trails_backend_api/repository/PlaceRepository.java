package com.ranjulajmn.lankan_local_trails_backend_api.repository;

import com.ranjulajmn.lankan_local_trails_backend_api.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByDeletedAtIsNull();
    Optional<Place> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT p FROM Place p WHERE p.id IN " +
            "(SELECT pc.placeId FROM PlaceCategory pc WHERE pc.categoryId = :categoryId) " +
            "AND p.deletedAt IS NULL")
    List<Place> findPlacesByCategoryId(@Param("categoryId") Long categoryId);
}
