package com.portfolio.springapplication.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accessToken = httpRequest.getHeader("Authorization");
        accessToken = jwtTokenProvider.getToken(accessToken);
        boolean isValid = jwtTokenProvider.validateToken(accessToken);

        if (isValid){
            Authentication authentication = jwtTokenProvider
        }
    }
}
