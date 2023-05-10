package com.portfolio.springapplication.security.config;

import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.provider.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfig {

    @Autowired
    UserAuthProvider userAuthProvider;

    @Autowired
    UserPrincipalDetailService userPrincipalDetailService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(userAuthProvider);
    }

    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.authorizeHttpRequests(authorize -> {
            try {
                authorize
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/user/signin/**", "/post/**", "/posts")
                        .permitAll()
                        .requestMatchers("/user/info")
                        .hasRole("GUEST")
                    .and()
                        .formLogin()
                        .loginPage("//url")
                        .loginProcessingUrl("//url")
                        .defaultSuccessUrl("//url")
                        .failureHandler(new UserAuthFailureHandler())
                        .permitAll()
                    .and()
                        .logout()
                        .logoutUrl("//url")
                        .logoutSuccessUrl("//url")
                        .deleteCookies("JSESSIONID");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        http.rememberMe()
                .key("key")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .userDetailsService(userPrincipalDetailService)
                .rememberMeParameter("remember-me");

        return http.build();
    }
}
