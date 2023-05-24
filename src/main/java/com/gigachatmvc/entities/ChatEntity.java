package com.gigachatmvc.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="student")
@Table(name = "chats")
@ToString
@Data
@NoArgsConstructor
public class ChatEntity {
    @Id
    private int id;
    private String userId;
    private String managerId;
    private int topicId;
    private int statusId;

    public ChatEntity(String userId){
        this.userId = userId;
    }
}
