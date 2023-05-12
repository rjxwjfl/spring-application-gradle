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

    @GetMapping("/api/post/view/{postId}")
    public ResponseEntity<?> getPostDtl(@PathVariable int postId){
        return ResponseEntity.ok().body(postRepo.getPostDetail(postId));
    }

    @GetMapping("/api/posts")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok().body(postRepo.getPosts());
    }

}