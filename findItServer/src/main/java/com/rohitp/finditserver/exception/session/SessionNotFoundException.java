package com.rohitp.finditserver.exception.session;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class SessionNotFoundException extends EntityNotFoundException {

    public SessionNotFoundException(UUID sessionId) {
        super(String.format("Session with id %s not found", sessionId));
    }

}
