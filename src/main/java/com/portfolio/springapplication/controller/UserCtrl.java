package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.user.UpdateReqDto;
import com.portfolio.springapplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserCtrl {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/info/{userId}")
    public ResponseEntity<?> getUserOutline(@PathVariable int userId){
        System.out.println("userId : " + userId);
        return ResponseEntity.ok().body(userRepo.getUserOutline(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateReqDto updateReqDto){
        return ResponseEntity.ok().body(userRepo.updateUserInfo(updateReqDto.getUserId(), updateReqDto.getName(), updateReqDto.getIntroduce(), updateReqDto.getImageUrl()));
    }
}
