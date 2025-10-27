package com.rohitp.finditserver.exception.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID userId) {
        super(String.format("User with id %s not found", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
