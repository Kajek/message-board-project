package com.sprint.message;

import com.sprint.user.auth.exception.NoAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void save(MessageDto messageDto) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        message.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getAll() {
        return messageRepository
                .findAll()
                .stream()
                .map(e -> MessageDto.from(e))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void update(Message message) {
        messageRepository.save(message);
    } // do wywalenia?

    @Override
    public void update(MessageDto messageDto) throws NoAccessException {
        Message message = messageRepository.findById(messageDto.getId()).orElse(null);
        if(!message.getUsername().equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new NoAccessException("You dont have permission to edit this message");
        }

        message.setContent(messageDto.getContent());
        message.setTimeStamp("last edited : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        messageRepository.save(message);
    }

    @Override
    public MessageDto getById(Integer id) {

        return MessageDto.from(Objects.requireNonNull(messageRepository.findById(id).orElse(null)));
//        return messageRepository.findById(id).orElse(null);
//        MessageDto messageDto = new MessageDto ();
//        Message message = messageRepository.findById(id).orElse(null);
//        messageDto.setId(message.getId());
//        messageDto.setContent(message.getContent());
//        messageDto.setUsername(message.getUsername());
//        messageDto.setTimeStamp(message.getTimeStamp());

    }

//    @Override
//    public void setTimestamp(Message message) {
//        message.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
//    }

//    @Override
//    public void addComment(Message message, Comment comment) {
//        message.getComments().add(comment);
//    }
}
