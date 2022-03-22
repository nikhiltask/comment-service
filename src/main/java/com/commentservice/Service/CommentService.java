package com.commentservice.Service;

import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public int countComments(String postId){
        int count=commentRepository.findBypostID(postId).size();
        return count;

    }
}
