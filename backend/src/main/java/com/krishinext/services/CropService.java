package com.krishinext.services;

import com.krishinext.models.Crop;
import com.krishinext.repositories.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropService {
    private final CropRepository cropRepository;

    public Crop addCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Optional<Crop> getCropById(String id) {
        return cropRepository.findById(id);
    }

    public Page<Crop> getCropsByCategory(String category, int page, int size) {
        return cropRepository.findByCategory(category, PageRequest.of(page, size));
    }

    public Crop updateCropStock(String id, Integer newQuantity) {
        Crop crop = cropRepository.findById(id).orElseThrow(() -> new RuntimeException("Crop not found"));
        crop.setQuantity(newQuantity);
        return cropRepository.save(crop);
    }

    public Crop updateCrop(String id, Crop cropDetails) {
        Crop crop = cropRepository.findById(id).orElseThrow(() -> new RuntimeException("Crop not found"));
        crop.setName(cropDetails.getName());
        crop.setDescription(cropDetails.getDescription());
        crop.setPricePerUnit(cropDetails.getPricePerUnit());
        crop.setQuantity(cropDetails.getQuantity());
        // Add other fields as necessary
        return cropRepository.save(crop);
    }

    public void deleteCrop(String id) {
        cropRepository.deleteById(id);
    }

    public List<Crop> filterDeliverable(List<Crop> crops, GeoJsonPoint userLocation) {
        return crops.stream()
                .filter(c -> calculateDistance(userLocation, c.getLocation()) <= c.getDeliveryRadius())
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
