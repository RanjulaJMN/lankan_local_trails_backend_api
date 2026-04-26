package com.ranjulajmn.lankan_local_trails_backend_api.controller;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.UserRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.VisitPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit-plans")
@RequiredArgsConstructor
public class VisitPlanController {
    private final VisitPlanService planService;
    private final UserRepository userRepository;

    @PostMapping("/generate")
    public ResponseEntity<PlanResponseDTO> generatePlan(@RequestBody PlanRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return ResponseEntity.ok(planService.generatePlan(request, user));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PlanResponseDTO>> getUserPlans() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return ResponseEntity.ok(planService.getUserPlans(user));
    }
}
