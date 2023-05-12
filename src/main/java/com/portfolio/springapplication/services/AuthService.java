package com.portfolio.springapplication.services;

import com.portfolio.springapplication.config.RedisConfig;
import com.portfolio.springapplication.dto.*;
import com.portfolio.springapplication.repository.UserRepo;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.exception.CustomException;
import com.portfolio.springapplication.security.exception.ErrorMap;
import com.portfolio.springapplication.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;


    public String signUp(SignUpReqDto signUpReqDto){
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String username = signUpReqDto.getUsername();
        String password = signUpReqDto.getPassword();
        String name = signUpReqDto.getName();

        String cryptPw = encoder.encode(password);

        return userRepo.signUp(username, cryptPw, name);
    }

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

    public String signOut(SignOutReqDto signOutReqDto){
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();
        String result = null;
        System.out.println("BEFORE DELETE : " +(String) redisTemplate.opsForValue().get(signOutReqDto.getUsername()));

        if (Boolean.TRUE.equals(redisTemplate.delete(signOutReqDto.getUsername()))){
            result = "User has signed out";
            System.out.println("AFTER DELETE : " + (String) redisTemplate.opsForValue().get(signOutReqDto.getUsername()));
        };

        return result;
    }

    public RtkRespDto refreshToken(RtkReqDto rtkReqDto){
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();

        String rtk = rtkReqDto.getRefreshToken();
        String storedRtk = (String) redisTemplate.opsForValue().get(rtkReqDto.getUsername());

        if (storedRtk == null || !storedRtk.equals(rtk)){
            // refresh token has expired
            throw new CustomException("RefreshToken does not exist.",
                    ErrorMap.builder()
                            .put("RefreshToken", "RefreshToken 이 만료되었습니다. 다시 로그인해주십시오.").build());
        }

        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(rtkReqDto.getUsername());

        return jwtTokenProvider.reissueToken(userPrincipalDetail);
    }
}
