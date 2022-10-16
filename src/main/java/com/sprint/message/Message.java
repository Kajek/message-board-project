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

    @Size(min = 3, max = 1000, message = "Message must be at least 3 characters and max 1000 characters")
    private String content;

//    @ManyToOne // add associacion to user class
//    @JoinColumn(name = "user_id")
//    private User user;
    private String username;

    private String timeStamp;

//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<Comment> comments;

//    public User getUser() {
//        return user;
//    }

    private static Message from(MessageDto messageDto){
        return new Message(messageDto.getId(),
                messageDto.getUsername(),
                messageDto.getContent(),
                messageDto.getTimeStamp());
    }

    //add reaction later - enum

}
