package com.commentservice.Service;

import com.commentservice.Exception.CommentNotFoundException;
import com.commentservice.Feign.LikeService;
import com.commentservice.Feign.UserService;
import com.commentservice.Model.CommentDto;
import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    LikeService likeService;

    @Autowired
    UserService UserFeign;


    public CommentDto postComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(commentModel);
        return feignStructure(commentModel);
    }

    public CommentDto feignStructure(CommentModel commentModel) {
        CommentDto commentDTO = new CommentDto(commentModel.getCommentID(),
                UserFeign.findByID(commentModel.getCommentedBy()).getFirstName(),
                commentModel.getComment(), commentModel.getCreatedAt(), commentModel.getUpdatedAt(),
                likeService.countLike(commentModel.getCommentID()));
        return commentDTO;
    }

    public CommentDto findByCommentId(String commentId) {
        CommentModel commentModel = commentRepository.findById(commentId).get();
        try {
            return feignStructure(commentModel);
        } catch (Exception e) {
            throw new CommentNotFoundException("Comment Not Found");
        }
    }

    public CommentModel commentUpdate(CommentModel commentModel, String postId, String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepository.save(commentModel);
    }

    public String deleteCommentById(String Id) {
        commentRepository.deleteById(Id);
        return "Delete ID " + Id + " from DB";
    }

    public int countComments(String postId) {
        int count = commentRepository.findBypostID(postId).size();
        return count;

    }

    public List<CommentDto> allComments(String postId, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        CommentDto commentDTO = new CommentDto();
        Pageable firstPage = PageRequest.of(page - 1, pageSize);
        List<CommentModel> commentModels = commentRepository.findBypostID(postId, firstPage);
        List<CommentDto> commentDTOS = new ArrayList<>();
        for (CommentModel commentModel : commentModels) {
            CommentDto commentDTO1 = new CommentDto(commentModel.getCommentID(),
                    UserFeign.findByID(commentModel.getCommentedBy()).getFirstName(),
                    commentModel.getComment(), commentModel.getCreatedAt(), commentModel.getUpdatedAt(),
                    likeService.countLike(commentModel.getCommentID()));
//                    likeService.countLike(commentModel.getCommentID()));
            commentDTOS.add(commentDTO1);
        }
        return commentDTOS;


    }


}
