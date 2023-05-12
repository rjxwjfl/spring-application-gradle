package com.portfolio.springapplication.security.config;

import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.jwt.JwtAuthEntryPoint;
import com.portfolio.springapplication.security.jwt.JwtAuthFilter;
import com.portfolio.springapplication.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception{
        http.cors();
        http.csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/signin", "/api/post/view/**", "/api/posts", "/api/auth/refresh", "/api/auth/signup").permitAll()
                .requestMatchers("/api/user/info/**", "/api/user/addpost")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
            .and()
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint);

        return http.build();
    }
}
