package com.gigachatmvc.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "messages")
@Table(name = "messages")
@ToString
@Getter
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String senderId;
    private int chatId;
    private String text;
    private Timestamp creationDate;

    public MessageEntity(int chatId, String senderId, String text){
        this.chatId = chatId;
        this.senderId = senderId;
        this.text = text;
    }
}
