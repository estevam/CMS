package ca.est.security;

import lombok.Data;

@Data
public class TokenRefreshResponse {
  private String access_token;
  private String refresh_token;
  private String token_type = "Bearer";

  public TokenRefreshResponse(String access_token, String refresh_token) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
  }
}
