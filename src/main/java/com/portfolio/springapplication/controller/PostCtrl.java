package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostCtrl {

    private final PostRepo postRepo;

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostDtl(@PathVariable int postId){
        System.out.println("Request has detected.");
        return ResponseEntity.ok().body(postRepo.getPostDetail(postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok().body(postRepo.getPosts());
    }

}