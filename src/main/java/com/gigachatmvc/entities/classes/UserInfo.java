package com.gigachatmvc.entities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UserInfo")
public class UserInfo {
    @Id
    String id;
    String name;
    String email;
}
