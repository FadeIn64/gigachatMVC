package com.gigachatmvc.repos;

import com.gigachatmvc.entities.classes.TopicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<TopicEntity, Integer> {
    List<TopicEntity> findAll();

    TopicEntity findFirstByName(String name);
}
