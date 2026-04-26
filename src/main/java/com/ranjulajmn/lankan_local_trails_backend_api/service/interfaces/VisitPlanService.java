package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;

import java.util.List;

public interface VisitPlanService {
    PlanResponseDTO generatePlan(PlanRequestDTO request, User user);
    List<PlanResponseDTO> getUserPlans(User user);
}
