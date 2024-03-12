package com.example.reactTest.repository;

import com.example.reactTest.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Objects;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    // query to get only text from comments
    @Query(value = "{}", fields = "{text : 1}")
    List<Comment> getAllComments();


}