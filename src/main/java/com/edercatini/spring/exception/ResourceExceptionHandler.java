package com.edercatini.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(NOT_FOUND.value(), e.getMessage(), currentTimeMillis());
        return status(NOT_FOUND).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError err = new StandardError(BAD_REQUEST.value(), "Formato de envio inválido.", currentTimeMillis());
        return status(BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        StandardError err = new StandardError(METHOD_NOT_ALLOWED.value(), "URI inválida.", currentTimeMillis());
        return status(METHOD_NOT_ALLOWED).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> forbidden(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(FORBIDDEN.value(), e.getMessage(), currentTimeMillis());
        return status(FORBIDDEN).body(err);
    }
}