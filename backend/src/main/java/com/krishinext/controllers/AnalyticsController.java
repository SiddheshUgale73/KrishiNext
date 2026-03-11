package com.krishinext.controllers;

import com.krishinext.dto.CropTradeStats;
import com.krishinext.dto.SellerRevenueStats;
import com.krishinext.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/most-traded")
    public ResponseEntity<List<CropTradeStats>> getMostTradedCrops() {
        return ResponseEntity.ok(analyticsService.getMostTradedCrops());
    }

    @GetMapping("/total-listings")
    public ResponseEntity<Long> getTotalCropListings() {
        return ResponseEntity.ok(analyticsService.getTotalCropListings());
    }

    @GetMapping("/demand-by-category")
    public ResponseEntity<List<Map>> getBuyerDemandByCategory() {
        return ResponseEntity.ok(analyticsService.getBuyerDemandByCategory());
    }

    @GetMapping("/seller-revenue")
    public ResponseEntity<List<SellerRevenueStats>> getSellerRevenue() {
        return ResponseEntity.ok(analyticsService.getSellerRevenue());
    }
}
