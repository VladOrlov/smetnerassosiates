package com.smetnerassosiates.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongURLException extends RuntimeException {

    public WrongURLException(String message) {
        super(message);
    }
}
