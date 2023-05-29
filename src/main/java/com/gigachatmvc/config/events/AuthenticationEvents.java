package com.gigachatmvc.config.events;

import com.gigachatmvc.entities.classes.UserInfo;
import com.gigachatmvc.repos.UserInfoRepository;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    @Autowired
    UserInfoRepository userInfoRepository;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        Authentication a =  success.getAuthentication();
        if(!(a.getClass() ==  KeycloakAuthenticationToken.class)) return;

        var keycloakAuthenticationToken = (KeycloakAuthenticationToken) a;
        IDToken idToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getIdToken();

        var userInfo = userInfoRepository.findById(a.getName());

        if(userInfo.isPresent()) return;

        UserInfo user = new UserInfo();
        user.setId(a.getName());
        user.setName(idToken.getPreferredUsername());
        user.setEmail(idToken.getEmail());

        user = userInfoRepository.save(user);
        System.out.println(user);
    }

}
