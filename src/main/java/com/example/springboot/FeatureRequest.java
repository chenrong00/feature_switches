package com.example.springboot;

public class FeatureRequest {
    private final String featureName;
    private final String email;
    private final boolean enable;

    public FeatureRequest(String featureName, String email, boolean enable) {
        this.featureName = featureName;
        this.email = email;
        this.enable = enable;
    }
    public String getFeatureName() {
        return featureName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnable() {
        return enable;
    }
}
