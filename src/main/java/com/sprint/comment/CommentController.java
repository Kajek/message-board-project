//package com.sprint.comment;
//
//import com.sprint.message.MessageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Slf4j
//@Controller
//public class CommentController {
//
//    private final  CommentService commentService;
//    private final MessageService messageService;
//
//    public CommentController(CommentService commentService, MessageService messageService) {
//        this.commentService = commentService;
//        this.messageService = messageService;
//    }
//
//    @GetMapping("/comment")
//    public String createNewComment(ModelMap modelMap){
//        modelMap.addAttribute("emptyComment", new Comment());
//        modelMap.addAttribute("messageDto", messageService.getById(id));
//        return "comment-create";
//    }
//
//    @PostMapping("/comment")
//    public String handleNewComment(@ModelAttribute("emptyComment") Comment comment){
//        log.info("Added new comment");
//        commentService.setTimestamp(comment);
//        commentService.save(comment);
//        return "redirect:/message-details";
//    }
//
//    // to wszystko chyba bedzie lepiej do msg controllera dodac po prostu, nie robić osobnego controllera dla komenetrzy
//}
