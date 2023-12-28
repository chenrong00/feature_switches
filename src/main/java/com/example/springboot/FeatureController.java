package com.example.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    // Internal map storing users' access to features simulating database
    private Map<String, Map<String, Boolean>> featureSwitches = new HashMap<>();

    @GetMapping("/getFeatureSwitches") // You can change the endpoint path as needed
    public Map<String, Map<String, Boolean>> getFeatureSwitches() {
        return featureSwitches;
    }

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getFeature(
            @RequestParam String email,
            @RequestParam String featureName
    ) {
        if (featureSwitches.containsKey((email)) && featureSwitches.get(email).containsKey(featureName)) {
            boolean canAccess = featureSwitches.get(email).get(featureName);
            Map<String, Boolean> response = new HashMap<>();
            response.put("canAccess", canAccess);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Boolean> response = new HashMap<>();
            response.put("canAccess", false);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    public ResponseEntity<Void> updateFeature(@RequestBody FeatureRequest featureRequest) {
        String email = featureRequest.getEmail();
        String featureName = featureRequest.getFeatureName();
        boolean enable = featureRequest.isEnable();

        // In practice this would be replaced by logic that checks the success of the actual database update operation
        // We will put this as true for testing purposes
        boolean databaseUpdateSuccessful = true;

        if (databaseUpdateSuccessful) {
            featureSwitches.computeIfAbsent(email, k -> new HashMap<>());
            featureSwitches.get(email).put(featureName, enable);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    public void clearFeatureSwitches() {
        featureSwitches.clear();
    }

}
