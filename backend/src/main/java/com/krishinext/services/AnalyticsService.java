package com.krishinext.services;

import com.krishinext.dto.CropTradeStats;
import com.krishinext.dto.SellerRevenueStats;
import com.krishinext.models.Crop;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final MongoTemplate mongoTemplate;

    public List<CropTradeStats> getMostTradedCrops() {
        Aggregation agg = newAggregation(
                lookup("crops", "crop.$id", "_id", "cropDetail"),
                unwind("cropDetail"),
                group("cropDetail.name").sum("orderQty").as("totalQuantity"),
                project("totalQuantity").and("_id").as("cropName"),
                sort(Direction.DESC, "totalQuantity"),
                limit(10));

        AggregationResults<CropTradeStats> results = mongoTemplate.aggregate(agg, "orders", CropTradeStats.class);
        return results.getMappedResults();
    }

    public long getTotalCropListings() {
        return mongoTemplate.count(new Query(), Crop.class);
    }

    public List<Map> getBuyerDemandByCategory() {
        Aggregation agg = newAggregation(
                lookup("crops", "crop.$id", "_id", "cropDetail"),
                unwind("cropDetail"),
                group("cropDetail.category").count().as("orderCount"),
                project("orderCount").and("_id").as("category"),
                sort(Direction.DESC, "orderCount"));

        AggregationResults<Map> results = mongoTemplate.aggregate(agg, "orders", Map.class);
        return results.getMappedResults();
    }

    public List<SellerRevenueStats> getSellerRevenue() {
        Aggregation agg = newAggregation(
                lookup("crops", "crop.$id", "_id", "cropDetail"),
                unwind("cropDetail"),
                lookup("sellers", "seller.$id", "_id", "sellerDetail"),
                unwind("sellerDetail"),
                project()
                        .and("sellerDetail._id").as("sellerId")
                        .and("sellerDetail.brandName").as("sellerBrand")
                        .andExpression("orderQty * cropDetail.pricePerUnit").as("revenue"),
                group("sellerId", "sellerBrand").sum("revenue").as("totalRevenue"),
                sort(Direction.DESC, "totalRevenue"));

        AggregationResults<SellerRevenueStats> results = mongoTemplate.aggregate(agg, "orders",
                SellerRevenueStats.class);
        return results.getMappedResults();
    }
}
