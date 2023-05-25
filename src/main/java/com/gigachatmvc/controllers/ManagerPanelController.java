package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.ChatService;
import com.gigachatmvc.chat.WaitService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.PermitAll;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Controller
@RequestMapping("/manage")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerPanelController {

    @Autowired
    ChatService service;

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    WaitService waitService;

    @GetMapping("/close/{chatId}")
    String close(@PathVariable("chatId") int chatId){
        service.close(chatId);
        template.convertAndSend("/topic/end/"+chatId, "CLOSE");
        return "redirect:/next";
    }

    @GetMapping("transferchat/{chatId}/{topicId}")
    String transferChat(Model model,
                        KeycloakAuthenticationToken authentication,
                        @PathVariable int chatId,
                        @PathVariable int topicId){
        service.transferChat(chatId, topicId);
        waitService.goWork(topicId);
        return "redirect:/messaging/manager";
    }


}
