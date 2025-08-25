package com.rohitp.finditserver.service;

import com.rohitp.finditserver.exception.base.UnauthorizedException;
import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    private static final Integer SESSION_EXPIRY_MINUTES = 60;

    Logger logger = LoggerFactory.getLogger(SessionService.class);

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

    public void deleteSessions(Integer userId) {
        Integer authenticatedUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!Objects.equals(userId, authenticatedUserId)) {
            throw new UnauthorizedException();
        }

        Integer deleteCount = this.sessionRepository.deleteSessionsByUserId(userId);
        logger.info("Deleted {} sessions for user {}", deleteCount, userId);
    }
}
