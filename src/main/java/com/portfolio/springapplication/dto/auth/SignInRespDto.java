package com.portfolio.springapplication.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignInRespDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
