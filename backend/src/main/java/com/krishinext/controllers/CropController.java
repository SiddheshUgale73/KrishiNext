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
@RequestMapping("/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;

    @PostMapping
    public ResponseEntity<Crop> addCrop(@RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.addCrop(crop));
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable String id) {
        return cropService.getCropById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Crop>> getCropsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cropService.getCropsByCategory(category, page, size));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Crop> updateCropStock(@PathVariable String id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cropService.updateCropStock(id, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable String id, @RequestBody Crop cropDetails) {
        return ResponseEntity.ok(cropService.updateCrop(id, cropDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable String id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter-by-location")
    public ResponseEntity<List<Crop>> filterByLocation(
            @RequestBody List<Crop> crops,
            @RequestParam double lat,
            @RequestParam double lng) {
        return ResponseEntity.ok(cropService.filterDeliverable(crops, new GeoJsonPoint(lng, lat)));
    }
}
