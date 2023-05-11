package com.portfolio.springapplication.security.config;

import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
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
                .requestMatchers("/user/signin", "/post/**", "/posts").permitAll()
                .requestMatchers("/user/info/**", "/user/addpost")
                .hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
                .addFilterBefore()
        ;

        return http.build();
    }
}
