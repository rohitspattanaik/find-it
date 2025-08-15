package com.rohitp.finditserver.exception.user;

import com.rohitp.finditserver.exception.base.EntityConflictException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserConflictException extends EntityConflictException {

    public UserConflictException(String email, Throwable cause) {
        super(String.format("User with email %s already exists", email), cause);
    }

}
