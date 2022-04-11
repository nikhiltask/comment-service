package com.commentservice.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.commentservice.Enum.BloodGroup;
import com.commentservice.Enum.Gender;
import com.commentservice.Exception.CommentNotFoundException;
import com.commentservice.Feign.LikeService;
import com.commentservice.Feign.UserService;
import com.commentservice.Model.CommentDto;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.User;
import com.commentservice.Repository.CommentRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentService.class})
@ExtendWith(SpringExtension.class)
class CommentServiceTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @MockBean
    private UserService userService;

    @Test
    void postComment() throws ParseException {
        CommentDto commentDTO = createCommentDTO();
        CommentModel commentModel = createCommentModel();

        Mockito.when(this.commentRepository.save(any(CommentModel.class))).thenReturn(commentModel);

        assertThat(this.commentService.postComment(commentModel, "12").getComment()).isEqualTo(commentDTO.getComment());

        verify(this.userService).findByID((String) any());
        verify(this.likeService).countLike((String) any());
        verify(this.commentRepository).save((CommentModel) any());
        assertEquals("12", commentModel.getPostID());
    }


//    @Test
//    void testFindByCommentId() {
//        User user = new User();
//        user.setAddress("42 Main St");
//        user.setBloodGroup(BloodGroup.B_POS);
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        user.setDateOfBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        user.setEmail("jane.doe@example.org");
//        user.setEmployeeNumber("42");
//        user.setFirstName("Jane");
//        user.setGender(Gender.MALE);
//        user.setLastName("Doe");
//        user.setMiddleName("Middle Name");
//        user.setPhoneNumber("4105551212");
//        user.setUserID("User ID");
//        when(this.userService.findByID((String) any())).thenReturn(user);
//        when(this.likeService.countLike((String) any())).thenReturn(3);
//
//        CommentModel commentModel = new CommentModel();
//        commentModel.setComment("Comment");
//        commentModel.setCommentID("Comment ID");
//        commentModel.setCommentedBy("Commented By");
//        commentModel.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        commentModel.setPostID("Post ID");
//        commentModel.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
//        Optional<CommentModel> ofResult = Optional.of(commentModel);
//        when(this.commentRepository.findById((String) any())).thenReturn(ofResult);
//        CommentDto actualFindByCommentIdResult = this.commentService.findByCommentId("42");
//        assertEquals("Comment", actualFindByCommentIdResult.getComment());
//        assertEquals(3, actualFindByCommentIdResult.getLikesCount());
//        assertEquals("01:01", actualFindByCommentIdResult.getUpdatedAt().toLocalTime().toString());
//        assertSame(user, actualFindByCommentIdResult.getCommentedBy());
//        assertEquals("01:01", actualFindByCommentIdResult.getCreatedAt().toLocalTime().toString());
//        assertEquals("Comment ID", actualFindByCommentIdResult.getCommentID());
//        verify(this.userService).findByID((String) any());
//        verify(this.likeService).countLike((String) any());
//        verify(this.commentRepository).findById((String) any());
//    }

    @Test
    void findByCommentId() throws ParseException {

        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO = createCommentDTO();

        when(this.commentRepository.findById("1")).thenReturn(Optional.of(commentModel));
        assertThat(this.commentService.findByCommentId("1").getComment()).isEqualTo(commentDTO.getComment());

    }

    @Test
    void commentUpdate() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO = createCommentDTO();

        when(this.commentRepository.save(commentModel)).thenReturn(commentModel);
        when(this.commentRepository.findById("1")).thenReturn(Optional.of(commentModel));

        assertThat(this.commentService.commentUpdate(commentModel, "12", "1").getComment()).isEqualTo(
                commentDTO.getComment()
        );
    }


    @Test
    void deleteCommentById() {
        doNothing().when(this.commentRepository).deleteById((String) any());
        when(this.commentRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> this.commentService.deleteCommentById("42"));
        verify(this.commentRepository).findById((String) any());
    }

    @Test
    void countComments() {
        when(this.commentRepository.findAll()).thenThrow(new CommentNotFoundException("An error occurred"));
        assertThrows(CommentNotFoundException.class, () -> this.commentService.countComments("2"));
        verify(this.commentRepository).findAll();
    }


    @Test
    void allComments() throws ParseException {
        CommentDto commentDTO = createCommentDTO();
        CommentModel commentModel = createCommentModel();
        ArrayList<CommentModel> commentModelList = new ArrayList<>();
        commentModelList.add(commentModel);
        when(this.commentRepository.findBypostID((String) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(commentModelList);
        assertEquals(1, this.commentService.allComments("42", null, 3).size());
        verify(this.userService).findByID((String) any());
        verify(this.likeService).countLike((String) any());
        verify(this.commentRepository).findBypostID((String) any(), (org.springframework.data.domain.Pageable) any());
    }

    private CommentModel createCommentModel() {
        return new CommentModel("1", "12", "123", "comment", null, null);
    }

    private CommentDto createCommentDTO() throws ParseException {
        return new CommentDto("1", "comment", createCommentUser(), 1, null, null);
    }

    private User createCommentUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2022-03-06");
        user.setUserID("123");
        user.setFirstName("Nikhil");
        user.setMiddleName("F");
        user.setLastName("Mike");
        user.setPhoneNumber("7894561230");
        user.setEmail("nik@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("4125");
        user.setBloodGroup(BloodGroup.A_POS);
        user.setGender(Gender.MALE);
        return user;

    }

}

