package com.sprint.comment;

import lombok.*;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Id
    private Integer id;

    private Integer messageId;

    private String content;

    private String username;

    private String timeStamp;


    public static CommentDto from(Comment comment){
        return new CommentDto(comment.getId(),
                comment.getMessageId(),
                comment.getContent(),
                comment.getUsername(),
                comment.getTimeStamp());
    }


}
