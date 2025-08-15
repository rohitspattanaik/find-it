package com.rohitp.finditserver.exception.authentication;

import com.rohitp.finditserver.exception.base.UnauthorizedException;

public class InvalidSessionException extends UnauthorizedException {

    public InvalidSessionException(Throwable cause) {
        super("Invalid session", cause);
    }
}
