package com.portfolio.springapplication.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.springapplication.common.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponseDto<?> errorResponseDto =
                new ErrorResponseDto<AuthenticationException>("Invalid token", authException);
        ObjectMapper objectMapper = new ObjectMapper();

        String responseJson = objectMapper.writeValueAsString(errorResponseDto);

        PrintWriter out = response.getWriter();
        out.println(responseJson);

    }
}
