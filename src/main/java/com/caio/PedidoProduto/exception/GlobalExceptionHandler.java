package com.caio.PedidoProduto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handle all generic exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomExceptionResponse>
        handleAllExceptions(Exception e, WebRequest request){
        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<CustomExceptionResponse>
    handleResourceNotFoundException(Exception e, WebRequest request){
        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedUserNameException.class)
    public final ResponseEntity<CustomExceptionResponse>
    handleDuplicatedUserNameException(Exception e, WebRequest request){
        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<CustomExceptionResponse>
    handleInvalidJwtAuthenticationException(Exception e, WebRequest request){
        CustomExceptionResponse response = new CustomExceptionResponse(new Date(),
                e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
