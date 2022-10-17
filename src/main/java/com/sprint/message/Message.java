package com.sprint.message;


import com.sprint.comment.Comment;
//import com.sprint.user.User;
import com.sprint.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min = 0, max = 1000, message = "Message must be at least 3 characters and max 1000 characters")
    private String content;

    private String username;

    private String timeStamp;


    private static Message from(MessageDto messageDto){
        return new Message(messageDto.getId(),
                messageDto.getUsername(),
                messageDto.getContent(),
                messageDto.getTimeStamp());
    }


}
