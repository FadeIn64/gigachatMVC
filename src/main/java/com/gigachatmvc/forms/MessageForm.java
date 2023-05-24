package com.gigachatmvc.forms;

import lombok.Data;
import lombok.Getter;

import javax.annotation.sql.DataSourceDefinitions;

@Data
public class MessageForm {
    private String senderId;
    private int chatId;
    private String text;
}
