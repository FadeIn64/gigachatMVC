package com.gigachatmvc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferEntity {
    @Id
    private int id;
    private int topicId;
    private int previousManagerId;
    private int newManagerId;
    private Timestamp creationTime;
}
