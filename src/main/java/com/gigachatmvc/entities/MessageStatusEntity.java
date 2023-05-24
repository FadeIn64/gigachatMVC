package com.gigachatmvc.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="message_statuses")
@Getter
@Table(name="message_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageStatusEntity {
    @Id
    private int id;
    private String name;
}
