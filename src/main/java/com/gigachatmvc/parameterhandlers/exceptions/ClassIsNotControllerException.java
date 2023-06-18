package com.gigachatmvc.parameterhandlers.exceptions;

public class ClassIsNotControllerException extends RuntimeException{

    public ClassIsNotControllerException() {
    }

    public ClassIsNotControllerException(String message) {
        super(message);
    }
}
