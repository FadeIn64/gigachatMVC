package com.gigachatmvc.controllers;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.PermitAll;
import javax.websocket.server.PathParam;

@Controller
public class IndexController {
    @GetMapping("/")
    @PermitAll
    String index(Model model){
        return "index";
    }

    @GetMapping("/next")
    String next(KeycloakAuthenticationToken authentication){
        var grants = authentication.getAuthorities();
        var role =  grants.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(x->x.equals("ROLE_USER")||x.equals("ROLE_MANAGER")).findFirst().orElseGet(()->"USER");
        if(role.equals("ROLE_USER"))
            return "redirect:/messaging";
        return "redirect:/messaging/manager";
    }
}
