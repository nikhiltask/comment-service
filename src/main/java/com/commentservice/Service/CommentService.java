package com.commentservice.Service;

import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentModel postComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(commentModel);
    }
}
