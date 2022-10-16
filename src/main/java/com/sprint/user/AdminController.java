package com.sprint.user;

import com.sprint.user.auth.exception.EmptyUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users/list")
    public String showUserList(ModelMap modelMap){
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user: userService.findAll()){
            UserDto userDto = new UserDto(user.getUsername(),
                    user.getRoles()
                    .stream()
                    .map(u -> u.getName() + " ")
                    .collect(Collectors.joining()));
            userDtoList.add(userDto);
        }
        modelMap.addAttribute("users", userDtoList);
        return "user-list";
    }

    @GetMapping("/admin/users/{username}/delete")
    public String deleteUser(@PathVariable String username){
        try {
            String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
            if (userService.existsByUsername(username)){
                if(!username.equals(loggedUser)){
                    log.info("Deleted user: " + username);
                    userService.deleteByUsername(username);
                }else {
                    log.info("You cannot delete yourself " + username);
                }
            }else{
                log.info("User with username "+ username + " does not exists!");
            }
        } catch (EmptyUsernameException e) {
            log.error(e.getMessage());
        }finally {
            return "redirect:/admin/users/list";
        }
    }

    @GetMapping("/admin/users/new")
    public String showCreateUserForm(ModelMap modelMap){
        modelMap.addAttribute("userDto", new UserDto());
        return "user-create";
    }


    @PostMapping("/admin/users")
    public String handleNewUser(@ModelAttribute("userDto") UserDto userDto){
        log.info("Handle new user " + userDto);
        userService.save(userDto);
        return "redirect:/admin/users/list";
    }



}
