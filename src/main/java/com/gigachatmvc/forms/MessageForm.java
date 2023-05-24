package com.gigachatmvc.forms;

import lombok.Getter;

@Getter
public class MessageForm {
    private String senderId;
    private int chatId;
    private String text;
}
