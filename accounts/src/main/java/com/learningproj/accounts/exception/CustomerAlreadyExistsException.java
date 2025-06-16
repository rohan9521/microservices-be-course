package com.learningproj.accounts.exception;

public class CustomerAlreadyExistsException extends Exception{
    public CustomerAlreadyExistsException(String message){
        super(message);
    }
}
