package ru.skypro.homework.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException() {
                return ResponseEntity.notFound().build();
    }

}
