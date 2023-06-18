package com.gigachatmvc.chatbot;

import com.gigachatmvc.chat.ChatService;
import com.gigachatmvc.forms.MessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChatBotAdapter implements ChatBot{

    ChatService chatService;

    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatBotAdapter(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostConstruct
    void postConstruct(){
        SimpleChatBot.chatService = this.chatService;
        SimpleChatBot.simpMessagingTemplate = this.simpMessagingTemplate;
    }

    @Override
    public boolean handleMessage(MessageForm messageForm) {
        var bot = SimpleChatBot.getInstance(messageForm);
        return bot.handleMessage(messageForm);
    }
}
