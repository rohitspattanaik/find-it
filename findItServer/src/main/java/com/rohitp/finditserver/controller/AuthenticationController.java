package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.authentication.AuthenticationRequest;
import com.rohitp.finditserver.dto.authentication.SessionDTO;
import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.filter.SessionFilter;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("v1/authn")
public class AuthenticationController {

    private final SessionService sessionService;

    @Autowired
    public AuthenticationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public SessionDTO authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Session session = this.sessionService.authenticateUser(authenticationRequest.getEmail());

        ResponseCookie cookie = ResponseCookie.from(SessionFilter.SESSION_COOKIE_NAME, session.getId().toString())
                .httpOnly(true)
                        .secure(true)
                                .sameSite("None")
                                        .path("/")
                .maxAge(30*60*60)
                                                .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return SessionDTO.fromSession(session);
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public SessionDTO getSession(@CookieValue(name = SessionFilter.SESSION_COOKIE_NAME, required = false) String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new SessionNotFoundException("Session was null or blank");
        }
        return SessionDTO.fromSession(this.sessionService.getSessionLazy(UUID.fromString(sessionId)));
    }

    @DeleteMapping(value = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void logout(@CookieValue(name = SessionFilter.SESSION_COOKIE_NAME, required = false) String sessionId, HttpServletResponse response) {
        if (sessionId == null || sessionId.isBlank()) {
            return;
        }
        this.sessionService.deleteSession(UUID.fromString(sessionId));

        ResponseCookie cookie = ResponseCookie.from(SessionFilter.SESSION_COOKIE_NAME)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void logoutAllSessions(@PathVariable UUID userId, HttpServletResponse response) {
        this.sessionService.deleteSessions(userId);

        Cookie cookie = new Cookie(SessionFilter.SESSION_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
