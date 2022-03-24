package com.commentservice.Controler;

import com.commentservice.Model.CommentDto;
import com.commentservice.Model.CommentModel;
import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @PostMapping("/posts/{postId}/comments")
//    public ResponseEntity<CommentModel> postComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
//        return new ResponseEntity<>(commentService.postComment(commentModel, postId), HttpStatus.ACCEPTED);
//    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> postComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.postComment(commentModel, postId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.commentUpdate(commentModel,postId,commentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentBytId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.deleteCommentById(commentId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/posts/{postId}/comments/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId){
        return  new ResponseEntity<>(commentService.countComments(postId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity< List<CommentDto>> showCommentsByPostId(@PathVariable("postId") String postId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(commentService.allComments(postId,page,pageSize), HttpStatus.ACCEPTED);
    }

}

