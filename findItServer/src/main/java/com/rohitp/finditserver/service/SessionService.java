package com.rohitp.finditserver.service;

import com.rohitp.finditserver.exception.base.UnauthorizedException;
import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.model.UserGroupMember;
import com.rohitp.finditserver.repository.LazyRespository;
import com.rohitp.finditserver.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.rohitp.finditserver.filter.SessionFilter.GROUP_ROLE_TEMPLATE;
import static com.rohitp.finditserver.filter.SessionFilter.ROLE_ADMIN;

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

    // TODO: implement authentication
    public Session authenticateUser(String email) {
        User user = this.userService.getUserByEmail(email);

        // TODO: Can probably setup a custom PermissionEvaluator in the future instead of a group role template
        List<String> claims = new java.util.ArrayList<>(user.getGroupMemberships()
                .stream()
                .map(userGroupMember -> String.format(GROUP_ROLE_TEMPLATE, userGroupMember.getRole(), userGroupMember.getUserGroup().getId()))
                .toList());

        if (user.getIsAdmin()) {
            claims.add(ROLE_ADMIN);
        }

        return this.sessionRepository.save(
                Session
                    .builder()
                        .userId(user.getId())
                        .isAdmin(user.getIsAdmin())
                        .claims(claims)
                    .user(user)  // Set the User object directly
                    .expiresAt(LocalDateTime.now().plusMinutes(SESSION_EXPIRY_MINUTES))
                    .build());
    }

    public Session getSession(UUID sessionId) {
        return this.sessionRepository.findById(sessionId).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    // Use this if the User object isn't needed
    public Session getSessionLazy(UUID sessionId) {
        return this.sessionRepository.findLazilyById(sessionId).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    public void deleteSession(UUID sessionId) {
        Integer deleteCount = this.sessionRepository.deleteSessionById(sessionId);
        logger.info("Deleted {} sessions for sessionId {}", deleteCount, sessionId);
    }

    public void deleteSessions(UUID userId) {
        UUID authenticatedUserId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!Objects.equals(userId, authenticatedUserId)) {
            throw new UnauthorizedException();
        }

        Integer deleteCount = this.sessionRepository.deleteSessionsByUserId(userId);
        logger.info("Deleted {} sessions for user {}", deleteCount, userId);
    }
}
