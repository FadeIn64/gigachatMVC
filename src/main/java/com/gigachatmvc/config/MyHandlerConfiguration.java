package com.gigachatmvc.config;

import com.gigachatmvc.chatbot.ChatBot;
import com.gigachatmvc.forms.MessageForm;
import com.gigachatmvc.parameterhandlers.congigurer.ControllerParameterHandlersChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Configuration
public class MyHandlerConfiguration {

    @Autowired
    ChatBot chatBot;

    @Autowired
    void init(ControllerParameterHandlersChain chain){
        chain.annotatedMethods(MessageMapping.class)
                .parameterType(MessageForm.class)
                .addHandler(x->{
                    System.out.println(x);
                    return true;
                })
                .addHandler(chatBot::handleMessage);
    }

}
