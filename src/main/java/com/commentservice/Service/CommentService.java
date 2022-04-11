package com.commentservice.Service;

import com.commentservice.ConstantFiles.ConstantNames;
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
        CommentDto commentDTO=new CommentDto(commentModel.getCommentID(),commentModel.getComment(),
                UserFeign.findByID(commentModel.getCommentedBy()),
                likeService.countLike(commentModel.getCommentID()),
                commentModel.getCreatedAt(),commentModel.getUpdatedAt());
        return commentDTO;
    }

    public CommentDto findByCommentId(String commentId) {
        CommentModel commentModel = commentRepository.findById(commentId).get();
        try {
            return feignStructure(commentModel);
        } catch (Exception e) {
            throw new CommentNotFoundException(ConstantNames.ERROR_CODE);
        }
    }

    public CommentDto commentUpdate(CommentModel commentModel, String postId, String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        commentRepository.save(commentModel);
        return feignStructure(commentModel);
    }

    public String deleteCommentById(String Id) {
        if(commentRepository.findById(Id).isPresent()) {
            commentRepository.deleteById(Id);
            return ConstantNames.SUCCESS_CODE;
        }else{
            throw new CommentNotFoundException(ConstantNames.ERROR_CODE);
        }
    }

    public int countComments(String postId) {
//        int count = commentRepository.findBypostID(postId).size();
//        return count;
            List<CommentModel> allData = commentRepository.findAll();
            int count = 0;
            for (CommentModel comment : allData) {
                if (comment.getPostID().equals(postId)) {
                    count++;
                }
            }
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
            CommentDto commentDTO1=new CommentDto(commentModel.getCommentID(),commentModel.getComment(),
                    UserFeign.findByID(commentModel.getCommentedBy()),
                    likeService.countLike(commentModel.getCommentID()),
                    commentModel.getCreatedAt(),commentModel.getUpdatedAt());
            commentDTOS.add(commentDTO1);
        }
        return commentDTOS;


    }


}
