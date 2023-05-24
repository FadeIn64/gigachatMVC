package com.gigachatmvc.repos;

import com.gigachatmvc.entities.MessageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByChatId(int chatId);
}
