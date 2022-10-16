package com.sprint.user.auth.exception;


public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(String message){
        super(message);
    }
}
