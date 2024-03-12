package com.example.reactTest.controller;

import com.example.reactTest.model.Comment;
import com.example.reactTest.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable ObjectId id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/comments/all")
    public List<Comment> getAllCommentsFromRepo() {
        return commentService.getAllCommentsFromRepo();
    }
}