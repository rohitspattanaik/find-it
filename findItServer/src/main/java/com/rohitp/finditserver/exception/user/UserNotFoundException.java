package com.rohitp.finditserver.exception.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Integer userId) {
        super(String.format("User with id %d not found", userId));
    }
}
