package com.gigachatmvc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import javax.websocket.server.PathParam;

@Controller
@Slf4j
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
        if(role.equals("ROLE_USER")){
            //log.info("Hello", authentication.getAccount().getKeycloakSecurityContext().getIdToken().getPreferredUsername());
            return "redirect:/messaging";}
        return "redirect:/messaging/manager";
    }
}
