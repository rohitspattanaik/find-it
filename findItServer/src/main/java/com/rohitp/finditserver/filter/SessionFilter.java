package com.rohitp.finditserver.filter;

import com.rohitp.finditserver.exception.authentication.InvalidSessionException;
import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private final SessionService sessionService;

    public static final String SESSION_COOKIE_NAME = "findit-session";

    @Autowired
    public SessionFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("SessionFilter");
        Optional<Cookie> sessionCookieOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findFirst();

        Cookie sessionCookie;
        if (sessionCookieOptional.isPresent()) {
            sessionCookie = sessionCookieOptional.get();
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Found session cookie");

        // Fetch the session
        Session session;
        try {
            session = this.sessionService.getSession(UUID.fromString(sessionCookie.getValue()));
        } catch (SessionNotFoundException e) {
            throw new InvalidSessionException(e);
        }

        System.out.println("Found session");

        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.authenticated(session.getUserId(), session.getId(), null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
