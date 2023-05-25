package com.gigachatmvc.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
public class WaitService {
    @Autowired
    SimpMessagingTemplate template;

    List<Deque<String>> managers  = new ArrayList();

    {
        managers.add(new ArrayDeque<>());
        managers.add(new ArrayDeque<>());
        managers.add(new ArrayDeque<>());
    }

    public void goWork(int topicId){
        String manager = managers.get(topicId).poll();
        System.out.println(manager);
        template.convertAndSend("/topic/wait/"+manager, "GO_WORK");
    }

    public void addUser(String manager, int topic){
        managers.get(topic).addFirst(manager);
    }


}
