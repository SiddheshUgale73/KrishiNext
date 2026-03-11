package com.krishinext.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropSenseResponse {
    private Double predictedPrice;
    private String trend; // Up, Down, Stable
    private String confidenceScore;
    private String marketInsight;
}
