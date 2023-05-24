package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.ChatService;
import com.gigachatmvc.entities.classes.ChatEntity;
import com.gigachatmvc.entities.classes.MessageEntity;
import com.gigachatmvc.forms.MessageForm;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PreAuthorize("hasRole('USER')")
    String userChat(Model model, KeycloakAuthenticationToken authentication){
        String user = authentication.getName();
        List<MessageEntity> messageEntities = chatService.fetchMessages(user);
        ChatEntity chatEntity = chatService.connect(user);
        modelConfig(model, chatEntity, user, messageEntities, getUserName(authentication), false);
        return "chat/chat";
    }

    @GetMapping("/messaging/manager")
    @PreAuthorize("hasRole('MANAGER')")
    String managerChat(Model model, KeycloakAuthenticationToken authentication){
        try{
            String user = authentication.getName();
            ChatEntity chatEntity = chatService.connectManager(user, authentication.getAuthorities());
            List<MessageEntity> messageEntities = chatService.fetchMessages(chatEntity.getId());
            modelConfig(model, chatEntity, user, messageEntities, getUserName(authentication), true);
            return "chat/chat";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return "redirect:/?chatNoFound";
        }
    }

    private static void modelConfig(Model model,
                                    ChatEntity chat,
                                    String user,
                                    List<MessageEntity> messages,
                                    String userName,
                                    boolean isManager){
        model.addAttribute("chat_id", chat.getId());
        model.addAttribute("user_id", user);
        model.addAttribute("messages", messages);
        model.addAttribute("user_name", userName);
        model.addAttribute("isManager", isManager);
        model.addAttribute("topic", chat.getTopicId());
        model.addAttribute("client_id",chat.getUserId());
    }

    private static String getUserName(KeycloakAuthenticationToken authentication){
        return authentication.getAccount()
                .getKeycloakSecurityContext()
                .getIdToken()
                .getPreferredUsername();
    }

}
