package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AdAppExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        log.trace("handleNotFoundException(): response status 404/NOT FOUND will be returned");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({BadImageException.class})
    public ResponseEntity<HttpStatus> handleBadImageException() {
        log.trace("handleBadImageException: response status 400/BAD REQUEST will be returned");
        return ResponseEntity.badRequest().build();
    }

}
