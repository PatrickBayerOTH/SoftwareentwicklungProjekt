package de.othr.im.model.oauthUser;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


/* Abdallah Alsoudi
* Diese Klasse ist eine hilfe Klasse für Google-Acount Registrieren 
* in verbunde mit CustomOAuth2User Konstruktor, dass die kommenden Daten eingeschrieben werden
* */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String clientName= userRequest.getClientRegistration().getClientName();
        return new CustomOAuth2User(user,clientName);
    }
}
