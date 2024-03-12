package com.example.reactTest.model;

// Comment.java
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Comment {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String text;
    private ObjectId author;
    private ObjectId post;
    private String createdAt;

    private User authorDetails;
    private Post postDetails;

    // Getters and setters
}