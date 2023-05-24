package com.gigachatmvc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="chat_statuses")
@Getter
@Table(name="chat_statuses")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatStatusEntity {
    @Id
    private int id;
    private String name;
}
