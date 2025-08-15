package com.rohitp.finditserver.exception.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Integer userId) {
        super(String.format("User with id %d not found", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
