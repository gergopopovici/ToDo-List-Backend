package edu.bbte.idde.pgim2289.spring.exceptions;

import edu.bbte.idde.pgim2289.spring.config.CustomConfig;
import edu.bbte.idde.pgim2289.spring.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private final CustomConfig customConfig;

    GlobalExceptionHandler(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
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
    @ResponseBody
    public ErrorDTO handleInvalidInputException(InvalidInputException ex, HttpServletRequest request) {
        return new ErrorDTO(
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(FailedFlagMiss.class)
    @ResponseBody
    public ErrorDTO handleFailedFlagMiss(FailedFlagMiss ex, HttpServletRequest request) {
        HttpStatus status;
        ErrorDTO errorDTO;
        if (customConfig.isDeleteMissingFatal()) {
            log.info("Hi");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorDTO = new ErrorDTO(
                    ex.getMessage(),
                    request.getRequestURI(),
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

        } else {
            status = HttpStatus.NOT_FOUND;
            log.info("Lo");
            errorDTO = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value()
            );
        }
        return new ResponseEntity<>(errorDTO, status).getBody();

    }
}
