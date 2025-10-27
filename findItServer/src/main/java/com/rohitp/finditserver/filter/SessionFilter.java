package com.rohitp.finditserver.filter;

import com.rohitp.finditserver.exception.authentication.InvalidSessionException;
import com.rohitp.finditserver.exception.session.SessionNotFoundException;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private final SessionService sessionService;

    public static final String SESSION_COOKIE_NAME = "findit-session";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String GROUP_ROLE_TEMPLATE = "GROUP_%s_%s";
    public static final String HAS_ADMIN_AUTHORITY = "hasAuthority('ROLE_ADMIN')";

    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Autowired
    public SessionFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> sessionCookieOptional = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        Cookie sessionCookie;
        if (sessionCookieOptional.isPresent()) {
            sessionCookie = sessionCookieOptional.get();
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        // Fetch the session
        Session session;
        try {
            session = this.sessionService.getSession(UUID.fromString(sessionCookie.getValue()));
        } catch (SessionNotFoundException e) {
            logger.error("Session not found", e);
            // continue execution without setting authentication
            filterChain.doFilter(request, response);
            return;
        }

        // Determine the user's roles
        User user = session.getUser();
        List<GrantedAuthority> grants = new ArrayList<>();
//        if (session.getIsAdmin()) {
//            grants.add(new SimpleGrantedAuthority(ROLE_ADMIN));
//        }
//        if (user.getGroups() != null) {
//            grants.addAll(user
//                    .getGroups()
//                    .stream()
//                    .map(group -> new SimpleGrantedAuthority(String.format(GROUP_ADMIN_TEMPLATE, group.getId())))
//                    .collect(Collectors.toSet()));
//        }
        if (session.getClaims() != null) {
            grants.addAll(session
                    .getClaims()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet()));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.authenticated(session.getUserId(), session.getId(), grants);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
