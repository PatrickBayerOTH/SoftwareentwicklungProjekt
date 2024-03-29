package de.othr.im.model.oauthUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/* Abdallah Alsoudi
* Klasse für Google-Account Anmeldung
* */
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;
    private String clienName;

    public CustomOAuth2User(OAuth2User oauth2User, String clienName) {
        this.oauth2User = oauth2User;
        this.clienName = clienName;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }

}
