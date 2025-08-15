package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.authentication.AuthenticationRequest;
import com.rohitp.finditserver.model.Session;
import com.rohitp.finditserver.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("v1/authn")
public class AuthenticationController {

    private final SessionService sessionService;

    @Autowired
    public AuthenticationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Session session = this.sessionService.authenticateUser(authenticationRequest.getEmail());

        Cookie cookie = new Cookie("session", session.getId().toString());
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
