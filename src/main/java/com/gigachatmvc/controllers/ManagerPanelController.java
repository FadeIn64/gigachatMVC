package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerPanelController {

    @Autowired
    ChatService service;

    @GetMapping("/close/{chatId}")
    String close(@PathVariable("chatId") int chatId){
        service.close(chatId);
        return "redirect:/?chatClosed";
    }

}
