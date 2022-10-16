package com.sprint.user;

import com.sprint.user.auth.exception.EmptyUsernameException;
import com.sprint.user.auth.exception.InvalidPasswordException;
import com.sprint.user.auth.exception.WrongPasswordException;
import com.sprint.user.auth.PasswordDto;
import com.sprint.user.auth.Role;
import com.sprint.user.auth.RoleRepository;
import com.sprint.util.PasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static final String INITIAL_PASSWORD = "pass";
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PasswordValidator passwordValidator;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public boolean existsByUsername(String username) throws EmptyUsernameException {
        if (username == null || username.isBlank()){
            throw new EmptyUsernameException("Username can not be empty!");
        }

        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        user.setRoles(getUserRoles("USER"));
        user.setPassword(encodeRawPassword(user.getPassword()));
        userRepository.save(user);
    }

    private List<Role> getUserRoles(String roles){

        String[] roleArray = roles.split(",");

        List<Role> roleList = new ArrayList<>();

        for(String role : roleArray){
            Role roleFromDB = roleRepository.findByName(role.trim());
            roleList.add(roleFromDB);
        }
        return roleList;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.deleteById(user.getId());
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setPassword(encodeRawPassword(INITIAL_PASSWORD));
        user.setUsername(userDto.getUsername());
        user.setRoles(getUserRoles(userDto.getRoles().toUpperCase()));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException {
        User user = userRepository.findByUsername(username);

        if(!bCryptPasswordEncoder.matches(passwordDto.getActualPassword(), user.getPassword())){
            throw new WrongPasswordException("Passwords are incorrect");
        }
        if(!passwordDto.getNewPassword().equals(passwordDto.getNewRepeatedPassword())){
            throw new WrongPasswordException("Passwords are incorrect");
        }
        if(!passwordValidator.isPasswordValid(passwordDto.getNewPassword())){
            throw new InvalidPasswordException("Password must contain A-Z,a-z,0-9, special characters(@#$%^&+=) and be at least 8 characters long");
        }
        user.setPassword(encodeRawPassword(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    private String encodeRawPassword(String rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }

}
