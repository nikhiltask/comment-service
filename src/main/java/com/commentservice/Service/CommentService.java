package com.commentservice.Service;

import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


import java.time.LocalDateTime;

import java.util.List;


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

    public CommentModel findByCommentId(String commentId){
        return commentRepository.findById(commentId).get();
    }



    public CommentModel commentUpdate(CommentModel commentModel, String postId, String commentId){
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepository.save(commentModel);
    }



    public String deleteCommentById(String Id){
        commentRepository.deleteById(Id);
        return "Delete ID "+Id+" from DB";
    }


    public int countComments(String postId){
        int count=commentRepository.findBypostID(postId).size();
        return count;

    }

    public List<CommentModel> allComments(){
        return commentRepository.findAll();
    }




}
