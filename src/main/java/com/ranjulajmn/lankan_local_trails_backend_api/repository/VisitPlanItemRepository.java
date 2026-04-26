package com.ranjulajmn.lankan_local_trails_backend_api.repository;

import com.ranjulajmn.lankan_local_trails_backend_api.entity.VisitPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitPlanItemRepository extends JpaRepository<VisitPlanItem, Long> {
}
