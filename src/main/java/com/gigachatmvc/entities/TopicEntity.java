package com.gigachatmvc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="topics")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TopicEntity {
    @Id
    private int id;
    private String name;
}
