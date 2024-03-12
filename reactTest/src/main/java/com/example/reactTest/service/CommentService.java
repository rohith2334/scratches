package com.example.reactTest.service;
import com.example.reactTest.model.Comment;
import com.example.reactTest.repository.CommentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;

@Service
public class CommentService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;
    public List<Comment> getAllComments() {
        Aggregation aggregation = newAggregation(
                lookup("users", "author", "_id", "authorDetails"),
                unwind("authorDetails"),
                lookup("posts", "post", "_id", "postDetails"),
                unwind("postDetails"),
                project().andExclude("_id")
        );

        AggregationResults<Comment> results = mongoTemplate.aggregate(aggregation, "comments", Comment.class);
        results.forEach(comment -> {
            comment.setAuthor(null);
            comment.setPost(null);
        });
        return results.getMappedResults();
    }

    public Comment getCommentById(ObjectId id) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("_id").is(id)),
                lookup("users", "author", "_id", "authorDetails"),
                unwind("authorDetails"),
                lookup("posts", "post", "_id", "postDetails"),
                unwind("postDetails"),
                project().andExclude("_id")
        );

        AggregationResults<Comment> results = mongoTemplate.aggregate(aggregation, "comments", Comment.class);
        return results.getUniqueMappedResult();
    }


    public List<Comment> getAllCommentsFromRepo() {
        return commentRepository.getAllComments();
    }
}
