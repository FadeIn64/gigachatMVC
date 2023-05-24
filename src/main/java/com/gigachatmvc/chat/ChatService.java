package com.gigachatmvc.chat;

import com.gigachatmvc.entities.ChatEntity;
import com.gigachatmvc.entities.MessageEntity;
import com.gigachatmvc.exceptions.ChatNotFoundException;
import com.gigachatmvc.forms.MessageForm;
import com.gigachatmvc.repos.ChatRepository;
import com.gigachatmvc.repos.ChatStatusesRepository;
import com.gigachatmvc.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatStatusesRepository chatStatusesRepository;
    @Autowired
    MessageRepository messageRepository;

    public ChatEntity connect(String userId, int chatId){
        var chatEntity = chatRepository.findById(chatId);
        if (chatEntity.isEmpty())
            return chatRepository.save(new ChatEntity(userId));

        if (chatEntity.get().getStatusId() == chatStatusesRepository.findFirstByName("CLOSED"))
            return chatRepository.save(new ChatEntity(userId));

        return chatEntity.get();
    }

    public List<ChatEntity> fetchChats(String userId){
        return chatRepository.findAllByUserId(userId);
    }

    public List<MessageEntity> fetchMessages(int chatId){
        return messageRepository.findAllByChatId(chatId);
    }

    public List<MessageEntity> fetchMessages(String userId) {
        var messages = new ArrayList<MessageEntity>();
        for(var chat : chatRepository.findAllByUserId(userId)){
            messages.addAll(messageRepository.findAllByChatId(chat.getId()));
        }
        return messages;
    }

    public MessageEntity receiveMessage(MessageForm form){
        return messageRepository.save(
                new MessageEntity(form.getChatId(), form.getSenderId(), form.getText()));
    }

    public void joinChat(String managerId, int chatId) throws ChatNotFoundException{
        var chat = chatRepository.findById(chatId);
        if (chat.isEmpty() || chat.get().getStatusId() == chatStatusesRepository.findFirstByName("CLOSED")){
            throw new ChatNotFoundException();
        }
        var chatObj = chat.get();
        chatObj.setUserId(managerId);
        chatRepository.save(chatObj);
    }
}
