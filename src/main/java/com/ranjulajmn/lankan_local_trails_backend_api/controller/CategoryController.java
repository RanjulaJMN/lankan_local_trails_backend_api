package com.ranjulajmn.lankan_local_trails_backend_api.controller;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDTO create(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam MultipartFile image
    )
    {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName(name);
        request.setDescription(description);

        return service.create(request, image);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDTO update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image

    ) {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName(name);
        request.setDescription(description);

        return service.update(id, request, image);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.softDelete(id, 1L); // temp user id
        return "Category soft deleted successfully";
    }
}
