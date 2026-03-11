package com.krishinext.services;

import com.krishinext.models.Product;
import com.krishinext.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> getProductsByCategory(String category, int page, int size) {
        return productRepository.findByCategory(category, PageRequest.of(page, size));
    }

    // Logic for filtering deliverable products based on radius (Haversine
    // equivalent in Spring Data)
    public List<Product> filterDeliverable(List<Product> products, GeoJsonPoint userLocation) {
        return products.stream()
                .filter(p -> calculateDistance(userLocation, p.getLocation()) <= p.getDeliveryRadius())
                .collect(Collectors.toList());
    }

    private double calculateDistance(GeoJsonPoint point1, GeoJsonPoint point2) {
        double lon1 = point1.getX();
        double lat1 = point1.getY();
        double lon2 = point2.getX();
        double lat2 = point2.getY();

        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
