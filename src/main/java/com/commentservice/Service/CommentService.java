package com.commentservice.Service;

import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


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
