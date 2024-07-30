package com.example.demo.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
    }

    public BookAlreadyExistsException(String message) {
        super(message);
    }
}