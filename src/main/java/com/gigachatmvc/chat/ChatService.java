package com.gigachatmvc.chat;

import com.gigachatmvc.entities.classes.ChatEntity;
import com.gigachatmvc.entities.classes.MessageEntity;
import com.gigachatmvc.entities.classes.TopicEntity;
import com.gigachatmvc.entities.enums.ChatStatusEnum;
import com.gigachatmvc.exceptions.ChatNotFoundException;
import com.gigachatmvc.forms.MessageForm;
import com.gigachatmvc.repos.ChatRepository;
import com.gigachatmvc.repos.ChatStatusesRepository;
import com.gigachatmvc.repos.MessageRepository;
import com.gigachatmvc.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    @Autowired
    TopicRepository topicRepository;

    public ChatEntity connect(String userId){  // подключение к последнему чату со статусом "открыто"
        var chats = chatRepository.findAllByUserId(userId);
        Optional<ChatEntity> chatEntity = chats.stream().filter(x->x.getStatusId() == 1).findFirst();
        return chatRepository.save(
                chatEntity.orElseGet(()->new ChatEntity(userId)));
    }

    public ChatEntity connect(int chatId){  // подключение к конкретному чату
        var chat = chatRepository.findById(chatId);
        return chat.orElseGet(()->null);
    }


    public ChatEntity connectManager(String managerId, Collection<GrantedAuthority> managerRoles) throws ChatNotFoundException{
        var openedChat = chatRepository.findFirstByManagerIdAndStatusId(managerId, ChatStatusEnum.OPEN.getId());
        if (openedChat.isPresent()) return openedChat.get();

        var role = managerRoles
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter((item)->item.matches("(.*)_TOPIC_(.*)"))
                .map((item)->item.split("ROLE_TOPIC_")[1]) // TODO возможно 1
                .findFirst()
                .get();

        System.out.println(role);

        var topic = topicRepository.findFirstByName(role).getId();

        openedChat = chatRepository.findFirstByTopicIdAndStatusId(topic, ChatStatusEnum.OPEN.getId());
        var chat = openedChat.orElseThrow(ChatNotFoundException::new);
        chat.setManagerId(managerId);
        chat = chatRepository.save(chat);
        return chat;
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

    public ChatEntity close(int id){
        ChatEntity chatEntity = chatRepository.findById(id).get();
        chatEntity.setStatusId(2);
        return chatRepository.save(chatEntity);
    }
}
