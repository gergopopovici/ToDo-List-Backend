package edu.bbte.idde.pgim2289.spring.exceptions;

import edu.bbte.idde.pgim2289.spring.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return new ErrorDTO(
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleInvalidInputException(InvalidInputException ex,HttpServletRequest request) {
        return new ErrorDTO(
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
}
