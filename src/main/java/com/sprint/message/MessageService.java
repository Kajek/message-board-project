package com.sprint.message;

import com.sprint.user.auth.exception.NoAccessException;

import java.util.List;

public interface MessageService {

    void save(Message message);
    void save(MessageDto messageDto);

    List<MessageDto> getAll();

    void deleteById(Integer id);

    void update(Message message);
    void update(MessageDto messageDto) throws NoAccessException;

    MessageDto getById(Integer id);

//    void setTimestamp(Message message);

//    void addComment(Message message, Comment comment);

}
