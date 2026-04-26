package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlanResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Place;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.VisitPlan;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.VisitPlanItem;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.PlaceRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.UserRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.VisitPlanItemRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.VisitPlanRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.VisitPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitPlanServiceImpl implements VisitPlanService {
    private final VisitPlanRepository planRepository;
    private final VisitPlanItemRepository itemRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        String lower = timeStr.toLowerCase().trim();
        if (lower.contains("always") || lower.contains("24/7") || lower.equals("open")) {
            return null; // represents always open
        }
        try {
            // Supports "5:00 AM", "5:00AM", "5:00", "5AM"
            String cleaned = timeStr.replace(" ", "").toUpperCase();
            DateTimeFormatter formatter;
            if (cleaned.contains("AM") || cleaned.contains("PM")) {
                formatter = DateTimeFormatter.ofPattern("h:mma");
            } else {
                formatter = DateTimeFormatter.ofPattern("H:mm");
            }
            return LocalTime.parse(cleaned, formatter);
        } catch (Exception e) {
            // fallback to 9:00 AM if parsing fails
            return LocalTime.of(9, 0);
        }
    }

    private boolean isAlwaysOpen(String timeStr) {
        if (timeStr == null) return false;
        String lower = timeStr.toLowerCase();
        return lower.contains("always") || lower.contains("24/7") || lower.equals("open");
    }

    // ---- Haversine distance ----
    private float haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float) (R * c);
    }
    @Override
    public PlanResponseDTO generatePlan(PlanRequestDTO request, User user) {
        if (user == null) {
            throw new RuntimeException("User must be authenticated");
        }


        List<Place> selected = placeRepository.findAllById(request.getPlaceIds());
        if (selected.isEmpty()) {
            throw new RuntimeException("Please select at least one place.");
        }

        // Sort: always‑open first, then by opening time
        selected.sort((p1, p2) -> {
            boolean a1 = isAlwaysOpen(p1.getOpeningTime());
            boolean a2 = isAlwaysOpen(p2.getOpeningTime());
            if (a1 && !a2) return -1;
            if (!a1 && a2) return 1;
            LocalTime t1 = parseTime(p1.getOpeningTime());
            LocalTime t2 = parseTime(p2.getOpeningTime());
            if (t1 == null && t2 == null) return 0;
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            return t1.compareTo(t2);
        });

        LocalTime current = LocalTime.of(8, 0);  // start at 8:00 AM
        List<PlanResponseDTO.PlanItemDTO> items = new ArrayList<>();
        Place previous = null;

        for (int i = 0; i < selected.size(); i++) {
            Place place = selected.get(i);
            int travelMins = 0;
            float distance = 0f;

            if (previous != null) {
                distance = haversineDistance(
                        previous.getLatitude(), previous.getLongitude(),
                        place.getLatitude(), place.getLongitude()
                );
                travelMins = (int) (distance / 40 * 60); // 40 km/h approx
                current = current.plusMinutes(travelMins);
            }

            LocalTime opening = parseTime(place.getOpeningTime());
            boolean alwaysOpen = isAlwaysOpen(place.getOpeningTime());
            if (!alwaysOpen && opening != null && current.isBefore(opening)) {
                current = opening;
            }

            // Visit duration: 90 minutes (can be customised per place later)
            LocalTime departure = current.plusMinutes(90);
            // Cap at 7:00 PM
            if (departure.isAfter(LocalTime.of(19, 0))) {
                departure = LocalTime.of(19, 0);
                if (current.isAfter(LocalTime.of(19, 0))) break; // no more places
            }

            PlanResponseDTO.PlanItemDTO dto = new PlanResponseDTO.PlanItemDTO();
            dto.setPlaceId(place.getId());
            dto.setPlaceName(place.getName());
            dto.setOrder(i);
            dto.setArrivalTime(current);
            dto.setDepartureTime(departure);
            dto.setTravelTimeMinutes(travelMins);
            dto.setDistanceKm(distance);
            dto.setAlwaysOpen(alwaysOpen);
            dto.setOpeningTimeText(place.getOpeningTime());
            dto.setClosingTimeText(place.getClosingTime());
            items.add(dto);

            current = departure;
            previous = place;
        }

        // Save plan and items
        VisitPlan plan = new VisitPlan();
        plan.setUser(user);
        plan.setPlanDate(request.getPlanDate() != null ? request.getPlanDate() : LocalDate.now());
        planRepository.save(plan);

        for (PlanResponseDTO.PlanItemDTO dto : items) {
            VisitPlanItem item = new VisitPlanItem();
            item.setPlan(plan);
            item.setPlace(placeRepository.findById(dto.getPlaceId()).orElseThrow());
            item.setOrder(dto.getOrder());
            item.setArrivalTime(dto.getArrivalTime());
            item.setDepartureTime(dto.getDepartureTime());
            item.setTravelTimeMinutes(dto.getTravelTimeMinutes());
            item.setDistanceKm(dto.getDistanceKm());
            itemRepository.save(item);
        }

        PlanResponseDTO response = new PlanResponseDTO();
        response.setId(plan.getId());
        response.setPlanDate(plan.getPlanDate());
        response.setItems(items);
        return response;

    }

    @Override
    public List<PlanResponseDTO> getUserPlans(User user) {
        List<VisitPlan> plans = planRepository.findByUserOrderByPlanDateDesc(user);
        return plans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PlanResponseDTO convertToDTO(VisitPlan plan) {
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setId(plan.getId());
        dto.setPlanDate(plan.getPlanDate());
        List<PlanResponseDTO.PlanItemDTO> items = plan.getItems().stream()
                .sorted(Comparator.comparing(VisitPlanItem::getOrder))
                .map(item -> {
                    PlanResponseDTO.PlanItemDTO i = new PlanResponseDTO.PlanItemDTO();
                    i.setPlaceId(item.getPlace().getId());
                    i.setPlaceName(item.getPlace().getName());
                    i.setOrder(item.getOrder());
                    i.setArrivalTime(item.getArrivalTime());
                    i.setDepartureTime(item.getDepartureTime());
                    i.setTravelTimeMinutes(item.getTravelTimeMinutes());
                    i.setDistanceKm(item.getDistanceKm());
                    // optional: add alwaysOpen, text times if needed
                    return i;
                })
                .collect(Collectors.toList());
        dto.setItems(items);
        return dto;
    }
}
