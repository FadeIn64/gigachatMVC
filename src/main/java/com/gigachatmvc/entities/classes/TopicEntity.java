package com.gigachatmvc.entities.classes;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicEntity {
    @Id
    private int id;
    private String name;
}
