package com.template.core.model.auth;

public class DoubleAuthToken {
    private String authToken;
    private String accessToken;

    public DoubleAuthToken() {
    }

    public DoubleAuthToken(String authToken, String accessToken) {
        this.authToken = authToken;
        this.accessToken = accessToken;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public DoubleAuthToken authToken(String token) {
        this.authToken = token;
        return this;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public DoubleAuthToken accessToken(String token) {
        this.accessToken = token;
        return this;
    }
}