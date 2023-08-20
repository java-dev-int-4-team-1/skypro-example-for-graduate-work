package ru.skypro.homework.exception;

public class BadImageException extends RuntimeException{
    public BadImageException(String originalFilename) {
        super(originalFilename);
    }
}
