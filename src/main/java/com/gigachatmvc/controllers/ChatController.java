package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.ChatService;
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
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendToUser("/topic/messages")
    @CrossOrigin
    public ResponseEntity<MessageEntity> receiveMessage(@RequestBody MessageForm message){
        chatService.receiveMessage(message);
        return new ResponseEntity<>(new MessageEntity(message.getChatId(), message.getSenderId(), message.getText()), HttpStatus.OK);
    }

    @GetMapping("/messaging")
    String goChat(Model model, KeycloakAuthenticationToken authentication){

        return "chat/chat";
    }
    
}
