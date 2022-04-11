package com.commentservice.Controler;

import com.commentservice.Enum.BloodGroup;
import com.commentservice.Enum.Gender;
import com.commentservice.Model.CommentDto;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.User;
import com.commentservice.Service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @MockBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetComments() throws Exception {
        List<CommentDto> userDto = createCommentList();

        Mockito.when(commentService.allComments("2", null, null)).thenReturn(userDto);

        mockMvc.perform(get("/posts/2/comments"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[1].comment", Matchers.is("commentTestTwo")));
    }

    private List<CommentDto> createCommentList() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        List<CommentDto> commentDto = new ArrayList<>();

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setCommentID("1");
        commentDto1.setComment("commentTestOne");
        commentDto1.setCommentedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        commentDto1.setLikesCount(3);

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setCommentID("2");
        commentDto2.setComment("commentTestTwo");
        commentDto2.setCommentedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        commentDto2.setLikesCount(3);
        commentDto.add(commentDto1);
        commentDto.add(commentDto2);

        return commentDto;
    }

    @Test
    public void testCreateComment() throws Exception {
        CommentModel comment = createOneCommentToPost();
        CommentDto commentDto = new CommentDto();
        CommentModel commentRequest = new CommentModel();
        Mockito.when(commentService.postComment(commentRequest, "1")).thenReturn(commentDto);
        mockMvc.perform(post("/posts/1/comments")
                        .content(asJsonString(comment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
//                .andExpect(jsonPath("$.comment", Matchers.is("CommentTest")));
    }

    private CommentModel createOneCommentToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentModel commentDto = new CommentModel();

        commentDto.setCommentID("1");
        commentDto.setComment("Hi");
        commentDto.setCommentedBy(String.valueOf(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com")));

        return commentDto;
    }

    @Test
    public void testGetCommentsByID() throws Exception {
        CommentDto commentDto = createOneComment();

        Mockito.when(commentService.findByCommentId("2")).thenReturn(commentDto);

        mockMvc.perform(get("/posts/1/comments/2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.comment", Matchers.is("CommentTest")));
    }

    private CommentDto createOneComment() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentID("2");
        commentDto.setComment("CommentTest");
        commentDto.setCommentedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        commentDto.setCreatedAt(null);
        return commentDto;
    }

    @Test
    public void testUpdateComment() throws Exception {
        CommentModel comment = createOneCommentToUpdate();
        CommentDto commentDto = new CommentDto();
        CommentModel com = new CommentModel();
        Mockito.when(commentService.commentUpdate(com, "1", "2")).thenReturn(commentDto);
        mockMvc.perform(put("/posts/1/comments/2")
                        .content(asJsonString(comment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private CommentModel createOneCommentToUpdate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentModel comment = new CommentModel();
        comment.setCommentID("2");
        comment.setComment("CommentTest");
        comment.setCommentedBy(String.valueOf(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com")));
        comment.setCreatedAt(null);
        comment.setUpdatedAt(null);
        return comment;
    }

    @Test
    public void testDeleteComment() throws Exception {

        Mockito.when(commentService.deleteCommentById("2")).thenReturn("Deleted");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/1/comments/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetCommentsCount() throws Exception {
        Integer count = createCommentsToCount();

        Mockito.when(commentService.countComments("1")).thenReturn(count);

        mockMvc.perform(get("/posts/1/comments/count"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private Integer createCommentsToCount() {
        List<CommentModel> comments = new ArrayList<>();

        CommentModel comment1 = new CommentModel();
        CommentModel comment2 = new CommentModel();
        CommentModel comment3 = new CommentModel();

        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        return comments.size();
    }

}
