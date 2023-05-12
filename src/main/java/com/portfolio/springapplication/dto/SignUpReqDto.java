package com.portfolio.springapplication.dto;

import lombok.Data;

@Data
public class SignUpReqDto {
    private String username;
    private String password;
    private String name;
}
