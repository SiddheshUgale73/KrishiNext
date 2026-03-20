package com.krishinext.controllers;

import com.krishinext.models.Crop;
import com.krishinext.services.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final CropService cropService;

    @PostMapping
    public ResponseEntity<Crop> addProduct(@RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.addCrop(crop));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Crop>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cropService.getCropsByCategory(category, page, size));
    }

    @PostMapping("/filter-by-location")
    public ResponseEntity<List<Crop>> filterByLocation(
            @RequestBody List<Crop> crops,
            @RequestParam double lat,
            @RequestParam double lng) {
        return ResponseEntity.ok(cropService.filterDeliverable(crops, new GeoJsonPoint(lng, lat)));
    }
}
