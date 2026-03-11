package com.krishinext.dto;

import lombok.Data;

@Data
public class CropSenseRequest {
    private String cropName;
    private String marketName;
    private String region;
    private Double currentPrice;
    private Integer demandScore;
}
