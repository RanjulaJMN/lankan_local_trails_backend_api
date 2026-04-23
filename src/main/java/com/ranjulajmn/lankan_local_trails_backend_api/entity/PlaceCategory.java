package com.ranjulajmn.lankan_local_trails_backend_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="place_category")
@Getter @Setter
public class PlaceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long pcId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "place_id")
    private Long placeId;
}
