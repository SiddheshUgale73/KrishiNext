package com.krishinext.controllers;

import com.krishinext.dto.CropSenseRequest;
import com.krishinext.dto.CropSenseResponse;
import com.krishinext.services.CropSenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/cropsense")
@RequiredArgsConstructor
public class CropSenseController {

    private final CropSenseService cropSenseService;

    @PostMapping("/predict")
    public ResponseEntity<CropSenseResponse> getPrediction(@RequestBody CropSenseRequest request) {
        return ResponseEntity.ok(cropSenseService.getPricePrediction(request));
    }
}
