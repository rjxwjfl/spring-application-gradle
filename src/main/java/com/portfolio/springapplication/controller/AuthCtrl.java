package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.auth.RtkReqDto;
import com.portfolio.springapplication.dto.auth.SignInReqDto;
import com.portfolio.springapplication.dto.auth.SignOutReqDto;
import com.portfolio.springapplication.dto.auth.SignUpReqDto;
import com.portfolio.springapplication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthCtrl {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto signUpReqDto){
        return ResponseEntity.ok().body(authService.signUp(signUpReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto){
        return ResponseEntity.ok().body(authService.signIn(signInReqDto));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(@RequestBody SignOutReqDto signOutReqDto){
        System.out.println("in signout : " + signOutReqDto);
        return ResponseEntity.ok().body(authService.signOut(signOutReqDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RtkReqDto rtkReqDto){
        return ResponseEntity.ok().body(authService.refreshToken(rtkReqDto));
    }

}
