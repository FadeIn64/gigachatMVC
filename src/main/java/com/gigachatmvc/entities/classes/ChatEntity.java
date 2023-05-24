package com.gigachatmvc.entities.classes;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name="student")
@Table(name = "chats")
@ToString
@Data
@NoArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String managerId;
    private int topicId = 1;
    private int statusId = 1;

    public ChatEntity(String userId){
        this.userId = userId;
    }
}
