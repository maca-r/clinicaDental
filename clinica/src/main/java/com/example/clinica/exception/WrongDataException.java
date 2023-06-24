package com.example.clinica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongDataException extends Throwable{
    public WrongDataException(String message) {
        super(message);
    }
}
