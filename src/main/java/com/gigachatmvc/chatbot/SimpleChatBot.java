package com.gigachatmvc.chatbot;

import com.gigachatmvc.chat.ChatService;
import com.gigachatmvc.entities.classes.ChatEntity;
import com.gigachatmvc.forms.MessageForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.Map;

public class SimpleChatBot implements ChatBot{

    private static final String MESSAGE_DESTINATION = "/topic/chat/";

    static ChatService chatService;
    static SimpMessagingTemplate simpMessagingTemplate;
    static Map<Integer, SimpleChatBot> instanceMap = new HashMap<>();

    public static SimpleChatBot getInstance(MessageForm messageForm){
        int chat = messageForm.getChatId();
        var instance = instanceMap.get(chat);
        if (instance == null){
            instance = new SimpleChatBot(chat);
            instanceMap.put(chat, instance);
        }
        return instance;
    }

    int chatId;
    String messageDestination;

    private SimpleChatBot(int chatId) {
        this.chatId = chatId;
        this.messageDestination = MESSAGE_DESTINATION + chatId;
    }

    @Override
    public boolean handleMessage(MessageForm messageForm) {

        var chat = chatService.connect(messageForm.getChatId());

        if (chat.getManagerId() == null)
            hello(chat, MESSAGE_DESTINATION +messageForm.getChatId());

        resolveProblem(messageForm);

        return true;
    }

    private void resolveProblem(MessageForm messageForm){
        String msg = messageForm.getText().toLowerCase();

        if(msg.contains("deposit") || msg.contains("депозит")) {
            resolve(2);
            return;
        }

        if (msg.contains("credit") || msg.contains("кредит")){
            resolve(3);
            return;
        }

        sendMessage(BotMessages.UnRecognizedMessage.message);
    }

    private void resolve(int topicId){
        chatService.transferChat(chatId, topicId);
        instanceMap.remove(chatId);
        sendMessage(BotMessages.TransferMessage.message);
    }

    private void hello(ChatEntity chat, String messageDestination){
        chat.setManagerId("bot");
        chatService.save(chat);
        sendMessage(BotMessages.HelloMessage.message);
    }

    private void sendMessage(String message){
        MessageForm payload = new MessageForm("bot", this.chatId, message);
        simpMessagingTemplate.convertAndSend( messageDestination, new ResponseEntity<>( chatService.receiveMessage(payload), HttpStatus.OK));
    }
}
