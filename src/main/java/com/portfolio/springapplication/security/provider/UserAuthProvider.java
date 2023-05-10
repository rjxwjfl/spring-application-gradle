package com.portfolio.springapplication.security.provider;

import com.portfolio.springapplication.entity.User;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(username);

        String dbPassword = userPrincipalDetail.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, dbPassword)){
            throw new BadCredentialsException("[USER] Invalid");
        }

        User user = userPrincipalDetail.user();

        if (user == null){
            throw new BadCredentialsException("[USER] Invalid account.");
        }

        return new UsernamePasswordAuthenticationToken(userPrincipalDetail, null, userPrincipalDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
