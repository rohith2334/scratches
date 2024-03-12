package com.example.reactTest.controller;

import com.example.reactTest.model.Post;
import com.example.reactTest.service.PostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPostsWithDetails();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable ObjectId id) {
        return postService.getPostById(id);
    }
}
