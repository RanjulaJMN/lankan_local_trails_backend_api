package com.ranjulajmn.lankan_local_trails_backend_api.controller;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.CategoryDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.softDelete(id, 1L); // temp user id
        return "Category soft deleted successfully";
    }
}
