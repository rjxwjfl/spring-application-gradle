package com.portfolio.springapplication.dto.auth;

import lombok.Data;

@Data
public class RtkReqDto {
    private String username;
    private String refreshToken;
}
