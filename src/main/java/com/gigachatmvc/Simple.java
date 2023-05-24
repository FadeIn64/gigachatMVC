package com.gigachatmvc;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.AuthenticationNotSupportedException;

@Controller
public class Simple {

    @GetMapping("/me")
    String me(Model model, KeycloakAuthenticationToken authentication){
        AccessToken accessToken = authentication.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("user", accessToken.getPreferredUsername());
        return "mail";
    }

}
