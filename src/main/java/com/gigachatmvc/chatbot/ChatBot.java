package com.gigachatmvc.chatbot;

import com.gigachatmvc.forms.MessageForm;
import org.springframework.stereotype.Component;


public interface ChatBot {

    boolean handleMessage(MessageForm messageForm);

}
