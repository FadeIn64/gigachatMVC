package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.ChatService;
import com.gigachatmvc.entities.ChatEntity;
import com.gigachatmvc.entities.MessageEntity;
import com.gigachatmvc.forms.MessageForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{id}")
    @CrossOrigin
    public ResponseEntity<MessageEntity> receiveMessage(@RequestBody MessageForm message){
        System.out.println(message);
        return new ResponseEntity<>( chatService.receiveMessage(message), HttpStatus.OK);
    }

    @GetMapping("/messaging")
    String goChat(Model model, KeycloakAuthenticationToken authentication){
        String user = authentication.getName();
        List<MessageEntity> messageEntities = chatService.fetchMessages(user);
        ChatEntity chatEntity = chatService.connect(user);
        model.addAttribute("chat_id", chatEntity.getId());
        model.addAttribute("user_id", user);
        model.addAttribute("messages", messageEntities);
        return "chat/chat";
    }
    
}
