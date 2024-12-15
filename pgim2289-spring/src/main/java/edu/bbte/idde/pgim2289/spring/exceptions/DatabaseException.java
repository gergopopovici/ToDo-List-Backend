package edu.bbte.idde.pgim2289.spring.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}