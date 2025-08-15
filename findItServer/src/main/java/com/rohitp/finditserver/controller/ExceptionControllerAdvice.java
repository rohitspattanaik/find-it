package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.exception.ExceptionDTO;
import com.rohitp.finditserver.exception.base.EntityConflictException;
import com.rohitp.finditserver.exception.base.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionDTO handleNotFound(EntityNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return getExceptionDTO(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDTO handleValidationException(MethodArgumentNotValidException exception) {
        logger.error(exception.getMessage(), exception);
        exception.getDetailMessageArguments();
        return getExceptionDTO(Arrays.toString(exception.getDetailMessageArguments()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityConflictException.class)
    public ExceptionDTO handleConflictException(EntityConflictException exception) {
        logger.error(exception.getMessage(), exception);
        return getExceptionDTO(exception, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionDTO handleUnauthorizedException(UnauthorizedException exception) {
        logger.error(exception.getMessage(), exception);
        return getExceptionDTO(exception, HttpStatus.UNAUTHORIZED);
    }

    private ExceptionDTO getExceptionDTO(Exception exception, HttpStatus httpStatus) {
        return ExceptionDTO
                .builder()
                .timestamp(Instant.now().toString())
                .status(httpStatus.value())
                .message(exception.getMessage())
                .build();
    }

    private ExceptionDTO getExceptionDTO(String message, HttpStatus httpStatus) {
        return ExceptionDTO
                .builder()
                .timestamp(Instant.now().toString())
                .status(httpStatus.value())
                .message(message)
                .build();
    }

}
