package de.szut.webshop.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencycodeNotFoundException extends RuntimeException{

    public CurrencycodeNotFoundException(String message){
        super(message);

    }
}
