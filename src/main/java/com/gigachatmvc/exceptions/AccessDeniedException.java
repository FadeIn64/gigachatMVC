package com.gigachatmvc.exceptions;

public class AccessDeniedException extends Exception{
    public AccessDeniedException(){
        super("access denied");
    }
}
