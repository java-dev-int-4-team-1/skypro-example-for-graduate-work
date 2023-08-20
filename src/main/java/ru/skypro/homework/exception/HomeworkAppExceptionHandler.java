package ru.skypro.homework.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HomeworkAppExceptionHandler {


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException() {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({BadImageException.class})
    public ResponseEntity<?> handleBadImageException() {

        return ResponseEntity.badRequest().build();
    }


}
