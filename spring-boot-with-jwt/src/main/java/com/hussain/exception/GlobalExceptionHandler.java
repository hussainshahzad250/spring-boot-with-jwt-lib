package com.hussain.exception;

import com.hussain.response.Response;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.SSLHandshakeException;
import java.io.EOFException;
import java.io.FileNotFoundException;

/**
 * @author shahzad.hussain
 * @since Dec 17, 2020
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_MSG = "Something went Wrong, Please try Later";

    @ExceptionHandler(value = FileNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex) {
        log.info("FileNotFound Exception occurs => {}", ex.getMessage());
        return new ResponseEntity<>(new Response("File does not exist", HttpStatus.NOT_FOUND), HttpStatus.OK);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        log.info("BadRequestException occurs => {}", ex.getMessage());
        return new ResponseEntity<>(new Response(ex.getMessage(), ex.getHttpStatus()), HttpStatus.OK);
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {
        log.info("ObjectNotFoundException occurs => {}", ex.getMessage());
        return new ResponseEntity<>(new Response(ex.getMessage(), ex.getHttpStatus()), HttpStatus.OK);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("BadCredentialsException occurs => {}", ex.getMessage());
        return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex) {
        log.info("Exception  occurs => {}", ex);
        return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFileNotFoundException(HttpRequestMethodNotSupportedException ex) {
        log.info("HttpRequestMethodNotSupportedException occurs => {}", ex.getMessage());
        return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFileNotFoundException(MissingServletRequestParameterException ex) {
        log.info("MissingServletRequestParameterException Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SSLHandshakeException.class)
    @ResponseBody
    public ResponseEntity<Response> handleSSLHandshakeException(SSLHandshakeException ex) {
        log.info("SSLHandshake Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EOFException.class)
    @ResponseBody
    public ResponseEntity<Object> handleEOFException(EOFException ex) {
        log.info("EOF Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        log.info("JWT Expired Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response("Token expired", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

}
