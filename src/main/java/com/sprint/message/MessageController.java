package com.sprint.message;

import com.sprint.comment.Comment;
import com.sprint.comment.CommentDto;
import com.sprint.comment.CommentService;
import com.sprint.user.auth.exception.NoAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class MessageController {

    private final MessageService messageService;
    private final CommentService commentService;

    public MessageController(MessageService messageService, CommentService commentService) {
        this.messageService = messageService;
        this.commentService = commentService;
    }

    @GetMapping("/messages")
    public String messageList(ModelMap modelMap){
        modelMap.addAttribute("messagesDto", messageService.getAll());

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("currentUser", currentUser);

        return "message-list";
    }

    @GetMapping("/messages/message")
    public String createNewMessage(ModelMap modelMap){
        modelMap.addAttribute("messageDto", new MessageDto());

        return "message-create";
    }

    @PostMapping("/messages/newMessage")
    public String handleNewMessage(@Valid @ModelAttribute("messageDto") MessageDto messageDto, Errors errors){
        if(errors.hasErrors()){
            log.error("Errors from frontend " + errors.getFieldErrors());
            return "message-create";
        }
        messageService.save(messageDto);
        log.info("Added new message");
        return "redirect:/messages";
    }

    @GetMapping("/messages/{id}")
    public String messageDetails(@PathVariable Integer id, ModelMap modelMap){
        modelMap.addAttribute("messageDto", messageService.getById(id));

        modelMap.addAttribute("commentsDto", commentService.getAllByMessageId(id));

        return "message-details";
    }

    @GetMapping("/messages/{id}/editor")
    public String messageEditForm(@PathVariable Integer id, ModelMap modelMap){
        modelMap.addAttribute("messageDto", messageService.getById(id));
        return "message-edit";
    }

    @PostMapping("/messages/updatedMessage") 
    public String handleUpdatedMessage(@Valid @ModelAttribute("messageDto") MessageDto messageDto, Errors errors, ModelMap modelMap){

        if(errors.hasErrors()){
            log.error("Errors from frontend " + errors.getFieldErrors());
            return "message-edit";
        }
        try{
            messageService.update(messageDto);
            log.info("Updated message");

        }catch (NoAccessException e){
            log.info(e.getMessage());
            modelMap.addAttribute("exceptionMessage", e.getMessage());
            return "message-edit";
        }
        return "redirect:/messages";
    }


    @GetMapping("/admin/messages/{id}/delete")
    public String deleteMessage(@PathVariable Integer id){
        messageService.deleteById(id);
        return "redirect:/messages";
    }

}
