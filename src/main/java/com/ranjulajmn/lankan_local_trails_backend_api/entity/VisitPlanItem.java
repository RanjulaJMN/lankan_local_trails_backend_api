package com.ranjulajmn.lankan_local_trails_backend_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "visit_plan_item")
@Getter
@Setter
public class VisitPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private VisitPlan plan;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "`order`", nullable = false)
    private Integer order;

    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "travel_time_minutes")
    private Integer travelTimeMinutes;

    @Column(name = "distance_km")
    private Float distanceKm;
}
