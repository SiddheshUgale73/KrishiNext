package com.krishinext.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishinext.dto.CropSenseRequest;
import com.krishinext.dto.CropSenseResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CropSenseService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CropSenseResponse getPricePrediction(CropSenseRequest request) {
        try {
            String jsonInput = objectMapper.writeValueAsString(request);

            // Execute Python script
            List<String> command = new ArrayList<>();
            command.add("python");
            command.add("scripts/cropsense_predict.py");
            command.add(jsonInput);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new java.io.File("c:/Users/Sarvadnya/OneDrive/Documents/Krishinext-SpringBoot"));
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return objectMapper.readValue(output.toString(), CropSenseResponse.class);
            } else {
                throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during CropSense AI prediction: " + e.getMessage(), e);
        }
    }
}
