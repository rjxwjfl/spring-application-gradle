package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCtrl {

    private final UserRepo userRepo;

    @GetMapping("/api/user/info/{userId}")
//    @PreAuthorize("#userId == authentication.principal.user.id")
    public ResponseEntity<?> getUserOutline(@PathVariable int userId){
        System.out.println("userId : " + userId);
        return ResponseEntity.ok().body(userRepo.getUserOutline(userId));
    }
}
