package com.rohitp.finditserver.exception.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityConflictException extends RuntimeException {

    public EntityConflictException(String message, Throwable cause) {
        super(message, cause);
    }

}
