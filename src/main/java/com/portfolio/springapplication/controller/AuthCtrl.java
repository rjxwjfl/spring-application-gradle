package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.RtkReqDto;
import com.portfolio.springapplication.dto.SignInReqDto;
import com.portfolio.springapplication.dto.SignOutReqDto;
import com.portfolio.springapplication.dto.SignUpReqDto;
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
        return ResponseEntity.ok(authService.signUp(signUpReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto){
        return ResponseEntity.ok(authService.signIn(signInReqDto));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(@RequestBody SignOutReqDto signOutReqDto){
        System.out.println("in signout : " + signOutReqDto);
        return ResponseEntity.ok(authService.signOut(signOutReqDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RtkReqDto rtkReqDto){
        return ResponseEntity.ok(authService.refreshToken(rtkReqDto));
    }

}
