package com.portfolio.springapplication.services;

import com.portfolio.springapplication.dto.SignInReqDto;
import com.portfolio.springapplication.dto.SignInRespDto;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public SignInRespDto signIn(SignInReqDto signInReqDto) {
        String username = signInReqDto.getUsername();
        String password = signInReqDto.getPassword();

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(username);

        if (userPrincipalDetail == null) {
            throw new BadCredentialsException("Invalid user");
        }

        String dbPassword = userPrincipalDetail.getPassword();

        if (!encoder.matches(password, dbPassword)) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtTokenProvider.generateToken(userPrincipalDetail);
    }

    public String signOut(){
        return "temp";
    }
}
