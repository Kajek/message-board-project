package com.sprint.comment;

import com.sprint.message.Message;
import com.sprint.message.MessageDto;
import com.sprint.user.auth.exception.NoAccessException;

import java.util.List;

public interface CommentService {

    void save(Comment comment);
    void save(CommentDto commentDto,Integer id);

    List<CommentDto> getAll();
    List<CommentDto> getAllByMessageId(Integer id);

    void deleteById(Integer id);

    void update(CommentDto commentDto) throws NoAccessException;

    CommentDto getById(Integer id);


}
