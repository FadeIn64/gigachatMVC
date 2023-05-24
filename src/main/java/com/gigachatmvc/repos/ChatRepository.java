package com.gigachatmvc.repos;

import com.gigachatmvc.entities.ChatEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChatRepository extends CrudRepository<ChatEntity, Integer>{
    List<ChatEntity> findAllByUserId(String userId);
    ChatEntity findFirstByStatusIdNotIn(List<Integer> statusId);
}
