package com.sprint.comment;

import com.sprint.message.Message;
import com.sprint.message.MessageDto;

import java.util.List;

public interface CommentService {

    void save(Comment comment);
    void save(CommentDto commentDto, MessageDto messageDto);

    List<CommentDto> getAll();
    List<CommentDto> getAllByMessageId(Integer id);

    void deleteById(Integer id);

    void update(Comment comment);

    Comment getById(Integer id);

    CommentDto getByMessageId(Integer id);

}
