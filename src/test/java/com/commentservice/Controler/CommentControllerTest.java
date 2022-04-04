//package com.commentservice.Controler;
////
////import org.junit.jupiter.api.Test;
////
////import static org.junit.jupiter.api.Assertions.*;
////
////class CommentControllerTest {
////
////    @Test
////    void postComment() {
////    }
////
////    @Test
////    void findByCommentId() {
////    }
////
////    @Test
////    void updateComment() {
////    }
////
////    @Test
////    void deleteCommentBytId() {
////    }
////
////    @Test
////    void commentCount() {
////    }
////
////    @Test
////    void showCommentsByPostId() {
////    }
////}
////package com.mavericsystems.commentservice.controller;
//
//import com.commentservice.Model.CommentDto;
//import com.commentservice.Model.CommentModel;
//import com.commentservice.Model.User;
//import com.commentservice.Service.CommentService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mavericsystems.commentservice.constant.CommentConstant;
//import com.mavericsystems.commentservice.dto.CommentDto;
//import com.mavericsystems.commentservice.dto.CommentRequest;
//import com.mavericsystems.commentservice.dto.UserDto;
//import com.mavericsystems.commentservice.model.Comment;
//import com.mavericsystems.commentservice.service.CommentService;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(CommentController.class)
//public class CommentControllerTest {
//    @MockBean
//    CommentService commentService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void testGetComments()throws Exception {
//        List<CommentDto> userDto = createCommentList();
//
//        Mockito.when(commentService.getComments("2", null, null)).thenReturn(userDto);
//
//        mockMvc.perform(get("/posts/2/comments"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(2)))
//                .andExpect(jsonPath("$[1].comment", Matchers.is("commentTestTwo")));
//    }
//
//    private List<CommentDto> createCommentList() {
//        List<CommentDto> commentDto = new ArrayList<>();
//
//        CommentDto commentDto1 = new CommentDto();
//        commentDto1.setCommentID("1");
//        commentDto1.setComment("commentTestOne");
////        commentDto1.setCommentedBy(new User("1","Prabhu","J","S","9090909090","prabhu@mail.com", null,"123",null,null));
//        commentDto1.setLikesCount(3);
//        commentDto1.setCreatedAt(LocalDate.now());
//        commentDto1.setUpdatedAt(LocalDate.now());
//
//        CommentDto commentDto2 = new CommentDto();
//        commentDto2.setCommentID("2");
//        commentDto2.setComment("commentTestTwo");
//        commentDto2.setCommentedBy(new User("2","Vishnu","J","S","9090909090","vishnu@mail.com", null,"132",null,null));
//        commentDto2.setLikesCount(3);
//        commentDto2.setCreatedAt(LocalDate.now());
//        commentDto2.setUpdatedAt(LocalDate.now());
//
//        commentDto.add(commentDto1);
//        commentDto.add(commentDto2);
//
//        return commentDto;
//    }
//
//    @Test
//    public void testCreateComment() throws Exception {
//        CommentModel comment = createOneCommentToPost();
//        CommentDto commentDto = new CommentDto();
//        CommentRequest commentRequest = new CommentRequest();
//        Mockito.when(commentService.createComment("1",commentRequest)).thenReturn(commentDto);
//        mockMvc.perform(post("/posts/1/comments")
//                        .content(asJsonString(comment))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
//
//    private Comment createOneCommentToPost() {
//        Comment commentDto = new Comment();
//
//        commentDto.setId("1");
//        commentDto.setComment("Hi");
//        commentDto.setCommentedBy(String.valueOf(new UserDto("1","Prabhu","J","S","9090909090","prabhu@mail.com", null,"123",null,null)));
//        return commentDto;
//    }
//
//    @Test
//    public void testGetCommentsByID() throws Exception {
//        CommentDto commentDto = createOneComment();
//
//        Mockito.when(commentService.getCommentDetails("1","2")).thenReturn(commentDto);
//
//        mockMvc.perform(get("/posts/1/comments/2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
//                .andExpect(jsonPath("$.comment", Matchers.is("CommentTest")));
//    }
//
//    private CommentDto createOneComment() {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId("2");
//        commentDto.setComment("CommentTest");
//        commentDto.setCommentedBy(new UserDto("1","Prabhu","J","S","9090909090","prabhu@mail.com", null,"123",null,null));
//        return commentDto;
//    }
//
//    @Test
//    public void testUpdateComment() throws Exception {
//        Comment comment = createOneCommentToUpdate();
//        CommentDto commentDto = new CommentDto();
//        CommentRequest commentRequest = new CommentRequest();
//        Mockito.when(commentService.updateComment("1",commentRequest, "2")).thenReturn(commentDto);
//        mockMvc.perform(put("/posts/1/comments/2")
//                        .content(asJsonString(comment))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//    private Comment createOneCommentToUpdate() {
//        Comment comment = new Comment();
//        comment.setId("2");
//        comment.setComment("CommentTest");
//        comment.setCommentedBy(String.valueOf(new UserDto("1","Prabhu","J","S","9090909090","prabhu@mail.com", null,"123",null,null)));
//        comment.setCreatedAt(null);
//        comment.setUpdatedAt(null);
//        return comment;
//    }
//
//    @Test
//    public void testDeleteComment() throws Exception {
//
//        Mockito.when(commentService.deleteComment("1","2")).thenReturn(CommentConstant.DELETEDCOMMENT);
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/posts/1/comments/2")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetCommentsCount() throws Exception {
//        Integer count = createCommentsToCount();
//
//        Mockito.when(commentService.getCommentsCount("1")).thenReturn(count);
//
//        mockMvc.perform(get("/posts/1/comments/count"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private Integer createCommentsToCount() {
//        List<Comment> comments = new ArrayList<>();
//
//        Comment comment1 = new Comment();
//        Comment comment2 = new Comment();
//        Comment comment3 = new Comment();
//
//        comments.add(comment1);
//        comments.add(comment2);
//        comments.add(comment3);
//        return comments.size();
//    }
//
//}
