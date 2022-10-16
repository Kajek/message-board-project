package com.sprint.user.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordDto {

    private String actualPassword;

    private String newPassword;

    private String newRepeatedPassword;
}
