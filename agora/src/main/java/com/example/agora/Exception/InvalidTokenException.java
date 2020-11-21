package com.example.agora.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "InvalidToken!!")
public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("InvalidToken!!");
    }
}
