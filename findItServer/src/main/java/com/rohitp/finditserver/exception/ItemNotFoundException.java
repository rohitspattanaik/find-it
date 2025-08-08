package com.rohitp.finditserver.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Item not found")
public class ItemNotFoundException extends EntityNotFoundException {

    ItemNotFoundException(String message) {
        
    }
}
