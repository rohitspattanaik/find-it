package com.rohitp.finditserver.service;

import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    private static final Integer SESSION_EXPIRY_MINUTES = 60;

    @Autowired
    public SessionService(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public Session authenticateUser(String email) {
        User user = this.userService.getUserByEmail(email);

        return this.sessionRepository.save(
                Session
                    .builder()
                    .userId(user.getId())
                    .expiresAt(LocalDateTime.now().plusMinutes(SESSION_EXPIRY_MINUTES))
                    .build());
    }

    public Session getSession(UUID sessionId) {
        return this.sessionRepository.findById(sessionId).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }
}
