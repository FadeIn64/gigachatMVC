package com.gigachatmvc.exceptions;

public class ChatNotFoundException extends Exception{
    public ChatNotFoundException(){
        super("chat not found");
    }
}
