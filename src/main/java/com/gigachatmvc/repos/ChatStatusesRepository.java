package com.gigachatmvc.repos;

import com.gigachatmvc.entities.classes.ChatStatusEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatStatusesRepository extends CrudRepository<ChatStatusEntity, Integer> {
    int findFirstByName(String name);
}
