package com.itb.inf2am.pizzaria.exceptions;

public class NotFound extends RuntimeException {

    // extends:  Herança, significa que a classe BadRequest é uma sub-classe de RuntimeException,
    //           ou seja, todos os recursos contidos em RuntimeException também faz parte da
    //           minha classe BadRequest

    public NotFound(String message) {
        super(message);
    }
}

