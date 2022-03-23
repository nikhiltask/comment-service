package com.commentservice.Repository;

import com.commentservice.Model.CommentModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentModel, String> {
    public List<CommentModel> findBypostID(String postId, Pageable page);
    public List<CommentModel> findBypostID(String postId);
}
