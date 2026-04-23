package com.ranjulajmn.lankan_local_trails_backend_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "place")
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Double latitude;
    private Double longitude;

    @Column(name= "opening_time")
    private String openingTime;

    @Column(name= "closing_time")
    private String closingTime;

    private Float distance;

    @Column(name= "travel_tips")
    private String travelTips;

    @Column(name= "image_url")
    private String imageUrl;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
