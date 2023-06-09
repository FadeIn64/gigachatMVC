package com.gigachatmvc.entities.classes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "messages")
@Table(name = "messages")
@Data
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
