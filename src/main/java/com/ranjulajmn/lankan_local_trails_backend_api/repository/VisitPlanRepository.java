package com.ranjulajmn.lankan_local_trails_backend_api.repository;

import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.VisitPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitPlanRepository extends JpaRepository<VisitPlan, Long> {
    List<VisitPlan> findByUserOrderByPlanDateDesc(User user);
    Optional<VisitPlan> findByIdAndUser(Long id, User user);
}

