package com.portfolio.springapplication.dto.auth;

import lombok.Data;

@Data
public class SignUpReqDto {
    private String username;
    private String password;
    private String name;
}
