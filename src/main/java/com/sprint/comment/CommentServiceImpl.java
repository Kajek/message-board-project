package com.sprint.comment;

import com.sprint.message.Message;
import com.sprint.message.MessageDto;
import com.sprint.message.MessageRepository;
import com.sprint.user.auth.exception.NoAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void save(CommentDto commentDto, Integer messageId) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setMessageId(messageId);
        comment.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository
                .findAll()
                .stream()
                .map(e -> CommentDto.from(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllByMessageId(Integer id) {
        return commentRepository.findAll()
                .stream()
                .map(e -> CommentDto.from(e))
                .filter(e -> Objects.equals(e.getMessageId(), id))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void update(CommentDto commentDto) throws NoAccessException {
        Comment comment = commentRepository.findById(commentDto.getId()).orElse(null);
        if(!comment.getUsername().equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new NoAccessException("You dont have permission to edit this message");
        }

        comment.setContent(commentDto.getContent());
        comment.setTimeStamp("last edited : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

        commentRepository.save(comment);
    }

    @Override
    public CommentDto getById(Integer id) {

        return CommentDto.from(Objects.requireNonNull(commentRepository.findById(id).orElse(null)));
    }




}
