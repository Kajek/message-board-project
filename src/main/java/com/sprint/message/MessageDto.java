package com.sprint.message;

import com.sprint.comment.Comment;
import lombok.*;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @Id
    private Integer id;

    private String username;

    private String content;

    private String timeStamp;


    public static MessageDto from(Message message){
        return new MessageDto(message.getId(),
                message.getContent(),
                message.getUsername(),
                message.getTimeStamp());
    }
}
