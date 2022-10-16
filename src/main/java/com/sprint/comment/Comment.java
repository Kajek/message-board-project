package com.sprint.comment;

//import com.sprint.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String content;

    private String username;

    private Integer messageId;

    private String timeStamp;

    public static Comment from(CommentDto commentDto){
        return new Comment(commentDto.getId(),
                commentDto.getContent(),
                commentDto.getUsername(),
                commentDto.getMessageId(),
                commentDto.getTimeStamp());
    }

}
