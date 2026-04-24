package com.ranjulajmn.lankan_local_trails_backend_api.controller;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceRequestDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.PlaceResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> create(@RequestBody PlaceRequestDTO dto) {
        return ResponseEntity.ok(placeService.create(dto));
    }

    @GetMapping
    public List<PlaceResponseDTO> getAll() {
        return placeService.getAll();
    }

    @GetMapping("/{id}")
    public PlaceResponseDTO getById(@PathVariable Long id) {
        return placeService.getById(id);
    }

    @PutMapping("/{id}")
    public PlaceResponseDTO update(@PathVariable Long id,
                                   @RequestBody PlaceRequestDTO dto) {
        return placeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        placeService.delete(id, 1L);
        return ResponseEntity.ok("Place deleted successfully");
    }
}
