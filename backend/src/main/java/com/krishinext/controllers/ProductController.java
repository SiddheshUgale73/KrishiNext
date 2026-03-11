package com.krishinext.controllers;

import com.krishinext.models.Product;
import com.krishinext.services.ProductService;
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
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getProductsByCategory(category, page, size));
    }

    @PostMapping("/filter-by-location")
    public ResponseEntity<List<Product>> filterByLocation(
            @RequestBody List<Product> products,
            @RequestParam double lat,
            @RequestParam double lng) {
        return ResponseEntity.ok(productService.filterDeliverable(products, new GeoJsonPoint(lng, lat)));
    }
}
