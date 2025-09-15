package com.itb.inf2am.pizzaria.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;



@ControllerAdvice
public class AppExceptionHandler  extends ResponseEntityExceptionHandler {

    ZoneId zoneBrasil = ZoneId.of("America/Sao_Paulo");

    String [] arrayMessage;



    // Tratamento do error: 500
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> globalException(Exception ex, WebRequest request) {

        LocalDateTime localDateTimeBrasil = LocalDateTime.now(zoneBrasil);
        String errorMessageDescription = ex.getLocalizedMessage(); // Mensagem original do erro
        
        // Log detalhado do erro para debug
        System.out.println("=== ERRO 500 DETALHADO ===");
        System.out.println("Mensagem: " + errorMessageDescription);
        System.out.println("Causa: " + ex.getCause());
        System.out.println("Stack trace: ");
        ex.printStackTrace();
        System.out.println("==========================");
        
        // Se não há mensagem específica, usar genérica
        if (errorMessageDescription == null || errorMessageDescription.trim().isEmpty()) {
            errorMessageDescription = "Ocorreu um erro interno no servidor";
        }

        arrayMessage = new String[]{errorMessageDescription};

        ErrorMessage errorMessage = new ErrorMessage(localDateTimeBrasil, arrayMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Tratamento do error: 400

    @ExceptionHandler(value = {BadRequest.class})
    public ResponseEntity<Object> badRequestException(BadRequest ex, WebRequest request) {
        LocalDateTime localDateTimeBrasil = LocalDateTime.now(zoneBrasil);
        String errorMessageDescription = ex.getLocalizedMessage(); // Mensagem original do erro

        if(errorMessageDescription == null) errorMessageDescription = ex.toString();
        arrayMessage = errorMessageDescription.split(":");
        ErrorMessage errorMessage = new ErrorMessage(localDateTimeBrasil, arrayMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    // Tratamento do error: 404

    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<Object> notFoundException(BadRequest ex, WebRequest request) {
        LocalDateTime localDateTimeBrasil = LocalDateTime.now(zoneBrasil);
        String errorMessageDescription = ex.getLocalizedMessage(); // Mensagem original do erro

        if(errorMessageDescription == null) errorMessageDescription = ex.toString();
        arrayMessage = errorMessageDescription.split(":");
        ErrorMessage errorMessage = new ErrorMessage(localDateTimeBrasil, arrayMessage, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }




}
