package com.krishinext.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CropTradeStats {
    private String cropName;
    private Long totalQuantity;
}
