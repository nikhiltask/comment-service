package com.commentservice.Repository;

import com.commentservice.Model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentModel, String> {
}
