package com.sprint.user.auth;

import com.sprint.user.auth.exception.EmptyUsernameException;
import com.sprint.user.User;
import com.sprint.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;
    private final AutologinService autologinService;

    public RegistrationController(UserService userService, AutologinService autologinService) {
        this.userService = userService;
        this.autologinService = autologinService;
    }
// zrobić reistration dto
    @GetMapping("/registration")
    public String showRegistrationForm(ModelMap modelMap){
        modelMap.addAttribute("emptyUser", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String handleNewUser(@ModelAttribute("emptyUser") User user, ModelMap modelMap){
        log.info("Recived new user: " + user.getUsername());

        try {
            if(userService.existsByUsername(user.getUsername())){
                log.info("User with username " + user.getUsername() + " exists!");
                modelMap.addAttribute("exceptionMessage", "User with username " + user.getUsername() + " exists!");
                return "registration";
            }
        } catch (EmptyUsernameException e) {
            log.info(e.getMessage());
            modelMap.addAttribute("exceptionMessage", e.getMessage());
            return "registration";
        }

        userService.save(user);

        autologinService.autologin(user.getUsername());

        return "redirect:/messages";
}
}
