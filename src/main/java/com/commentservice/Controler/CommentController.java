package com.commentservice.Controler;

import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentBytId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.deleteCommentById(commentId), HttpStatus.ACCEPTED);
    }
}
