package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.SignInReqDto;
import com.portfolio.springapplication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthCtrl {

    @Autowired
    private AuthService authService;

    @PostMapping("/user/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto, BindingResult bindingResult){
        return ResponseEntity.ok(authService.signIn(signInReqDto));
    }

    @GetMapping("/user/signout")
    public ResponseEntity<?> signOut(){
        return ResponseEntity.ok(authService.signOut());
    }

}
