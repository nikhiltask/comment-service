package com.commentservice.Controler;

import com.commentservice.Model.CommentDto;
import com.commentservice.Model.CommentModel;
import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import java.util.List;


@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentDto> postComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.postComment(commentModel, postId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.commentUpdate(commentModel,postId,commentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentBytId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.deleteCommentById(commentId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId){
        return  new ResponseEntity<>(commentService.countComments(postId), HttpStatus.ACCEPTED);
    }


    @GetMapping()
    public ResponseEntity< List<CommentDto>> allComments(@PathVariable("postId") String postId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(commentService.allComments(postId,page,pageSize), HttpStatus.ACCEPTED);
    }

}

