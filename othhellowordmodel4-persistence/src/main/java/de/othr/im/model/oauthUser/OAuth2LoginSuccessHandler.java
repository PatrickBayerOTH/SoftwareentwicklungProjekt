package de.othr.im.model.oauthUser;

import de.othr.im.controller.UserController;
import de.othr.im.model.AuthenticationProvider;
import de.othr.im.model.User;
import de.othr.im.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


/* Abdallah Alsoudi
*
* Diese Klasse ist auch für Google-Account Anmeldung
* Die Daten von Google werden hier behandelt
* Split Methode wird der Name und Nachname beim Fall von Fehlern von Namen in Google trenne und in der Db anpassen
* Danach wenn Account existiert, dann die Methode in UserController wird aufgerufen und die daten aktualisieren oder gleich bleiben
* existiert der User noch nicht, dann wird ein Account angelegt
*
* */
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @Override
    @GetMapping("/")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String email = oauthUser.getEmail();
        String name = oauthUser.getName();

        String googleName = name;
        String firstname = null;
        String lastname = null;
        if (googleName.contains(" ")) {
            String[] googleNameKette = name.split(" ");
            for (int i = 0; i < googleNameKette.length; i++) {
                firstname = googleNameKette[0];
                lastname = googleNameKette[1];
            }
        }

        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
                userController.processOAuthPostLogin(email, firstname, lastname);
        } else {
            userController.updateUserAfterOAuthLogin(user.get(), firstname, lastname, AuthenticationProvider.GOOGLE);
        }

        response.sendRedirect("/home");

    }

}
