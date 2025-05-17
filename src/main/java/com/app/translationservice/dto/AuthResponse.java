package com.app.translationservice.dto;

public class AuthResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  // Constructors
  public AuthResponse() {
  }

  public AuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public AuthResponse(String accessToken, String tokenType) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
  }

  // Getters and Setters
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

}
