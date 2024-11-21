package com.erudio.rest_with_spring_boot_and_java_erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectNullException extends RuntimeException{
    public RequiredObjectNullException(String ex){
        super(ex);
    }

    public RequiredObjectNullException(){
        super("It is not allowed to persist a null object");
    }
}
