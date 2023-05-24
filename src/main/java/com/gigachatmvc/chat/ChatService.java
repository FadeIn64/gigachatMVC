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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatStatusesRepository chatStatusesRepository;
    @Autowired
    MessageRepository messageRepository;

    public ChatEntity connect(String userId){
        var chats = chatRepository.findAllByUserId(userId);
        Optional<ChatEntity> chatEntity = chats.stream().filter(x->x.getStatusId() == 1).findFirst();
        return chatRepository.save(
                chatEntity.orElseGet(()->new ChatEntity(userId)));
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
        MessageEntity _new = new MessageEntity();
        _new.setChatId(form.getChatId());
        _new.setSenderId(form.getSenderId());
        _new.setText(form.getText());
        _new.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        return messageRepository.save(_new);
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
