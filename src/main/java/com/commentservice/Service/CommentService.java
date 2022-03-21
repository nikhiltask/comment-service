package com.commentservice.Service;

import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public String deleteCommentById(String Id){
        commentRepository.deleteById(Id);
        return "Delete ID "+Id+" from DB";
    }
}
