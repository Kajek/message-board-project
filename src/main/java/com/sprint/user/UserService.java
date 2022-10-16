package com.sprint.user;

import com.sprint.user.auth.exception.EmptyUsernameException;
import com.sprint.user.auth.exception.InvalidPasswordException;
import com.sprint.user.auth.exception.WrongPasswordException;
import com.sprint.user.auth.PasswordDto;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username) throws EmptyUsernameException;


    void save(User user);

    List<User> findAll();

    void deleteByUsername(String username);

    void save(UserDto userDto);

    void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException;
}
