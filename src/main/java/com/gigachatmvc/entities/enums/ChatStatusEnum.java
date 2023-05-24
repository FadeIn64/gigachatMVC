package com.gigachatmvc.entities.enums;

public enum ChatStatusEnum {
    OPEN(1), CLOSE(2);

    private final int id;

    ChatStatusEnum(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
