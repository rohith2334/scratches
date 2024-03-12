package com.example.reactTest.service;

import com.example.reactTest.model.Post;
import com.example.reactTest.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService; // Inject UserService to fetch user details


    public List<Post> getAllPostsWithDetails() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(post ->{
            post.setAuthorDetails(userService.getUserById(post.getAuthor()));
            // remove author id from post object
//            post.setAuthor(null);
        });
        return posts;
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(ObjectId id) {
        return postRepository.findById(id).orElse(null);
    }
}
