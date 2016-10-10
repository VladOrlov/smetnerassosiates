package com.smetnerassosiates.controllers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ContentParseException extends RuntimeException {

    public ContentParseException(String message) {
        super(message);
    }
}
