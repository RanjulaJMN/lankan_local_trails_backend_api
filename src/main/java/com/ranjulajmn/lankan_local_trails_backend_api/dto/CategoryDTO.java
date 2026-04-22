package com.ranjulajmn.lankan_local_trails_backend_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
}
