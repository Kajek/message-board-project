package com.sprint.comment;

import com.sprint.message.MessageService;
import lombok.extern.slf4j.Slf4j;
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
public class CommentController {

    private final  CommentService commentService;
    private final MessageService messageService;

    public CommentController(CommentService commentService, MessageService messageService) {
        this.commentService = commentService;
        this.messageService = messageService;
    }

    @GetMapping("/messages/{id}/comments/new")
    public String createNewComment(@PathVariable Integer id, ModelMap modelMap){
        modelMap.addAttribute("messageDto", messageService.getById(id));
        modelMap.addAttribute("commentDto", new CommentDto());
        return "comment-create";
    }

    @PostMapping("/messages/{id}/comments/new")
    public String handleNewComment(@PathVariable Integer id, ModelMap modelMap,
                                   @Valid @ModelAttribute("commentDto") CommentDto commentDto,
                                   Errors errors){
        modelMap.addAttribute("messageDto", messageService.getById(id));

        if(errors.hasErrors()){
            log.error("Errors from frontend " + errors.getFieldErrors());
            return "comment-create";
        }
        commentService.save(commentDto, id);
        log.info("Added new comment");
        return "redirect:/messages/{id}";
    }

    @GetMapping("/admin/comments/{id}/delete")
    public String deleteComment(@PathVariable Integer id){
        commentService.deleteById(id);
        return "redirect:/messages/{id}";
    }
}
