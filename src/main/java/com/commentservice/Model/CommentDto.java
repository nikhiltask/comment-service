package com.commentservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommentDto {

    @Id
    private String commentID;

    @NotEmpty(message = "commentedBy is required")
    private String commentedBy;

    @NotEmpty(message = "comment is required")
    private String comment;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likesCount;
}
