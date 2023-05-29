package com.example.adweb_backend.exception;

import com.example.adweb_backend.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> formatError(MethodArgumentNotValidException e){
        return new ResponseEntity<Object>(new MessageResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> jsonParseError(HttpMessageNotReadableException e){
        return new ResponseEntity<Object>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> numberFormatError(NumberFormatException e){
        return new ResponseEntity<Object>(new MessageResponse("数字转换错误"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> missReqHeader(MissingRequestHeaderException e){
        if (e.getHeaderName().equals("Authorization"))
            return new ResponseEntity<Object>(new MessageResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<Object>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
