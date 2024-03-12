package com.example.reactTest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String title;
    private String content;
    @JsonIgnore
    @Field("author")
    private ObjectId author;
    private String createdAt;
    private User authorDetails;

    // Getters and setters

}
