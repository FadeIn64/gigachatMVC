package com.gigachatmvc.repos;

import com.gigachatmvc.entities.classes.ChatEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ChatRepository extends CrudRepository<ChatEntity, Integer>{
    List<ChatEntity> findAllByUserId(String userId);
    Optional<ChatEntity> findFirstByTopicIdAndStatusId(int topicId, int statusId);
    Optional<ChatEntity> findFirstByManagerIdAndStatusId(String managerId, int statusId);
}
