package com.krishinext.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerRevenueStats {
    private String sellerId;
    private String sellerBrand;
    private Double totalRevenue;
}
