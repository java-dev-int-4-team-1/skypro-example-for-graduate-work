package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdAppExceptionHandler {


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({BadImageException.class})
    public ResponseEntity<HttpStatus> handleBadImageException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({EditForbiddenException.class})
    public ResponseEntity<HttpStatus> handleEditForbiddenException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
