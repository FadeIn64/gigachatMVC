package com.gigachatmvc.controllers;

import com.gigachatmvc.chat.WaitService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasRole('MANAGER')")
public class WaitController {

    @Autowired
    WaitService waitService;

    @GetMapping("/waiting/{topic}")
    String sub(@PathVariable("topic") int topic, KeycloakAuthenticationToken authentication){
        String manager = authentication.getName();
        waitService.addUser(manager, topic-1);
        return "redirect:/waiting";
    }
    @GetMapping("/waiting")
    String wait(KeycloakAuthenticationToken authentication, Model model){
        String manager = authentication.getName();

        model.addAttribute("manager", manager);
        model.addAttribute("user_name", ChatController.getUserName(authentication));
        return "chat/managerWait";
    }


}
