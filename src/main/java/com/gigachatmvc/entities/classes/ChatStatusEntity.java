package com.gigachatmvc.entities.classes;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="chat_statuses")
@Table(name="chat_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatStatusEntity {
    @Id

    private int id;
    private String name;
}
