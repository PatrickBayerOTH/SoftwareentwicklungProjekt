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

        //userController.processOAuthPostLogin(email, name);


        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            userController.processOAuthPostLogin(email, name);
        } else {
            userController.updateUserAfterOAithLoginSuccess(user.get(), name, AuthenticationProvider.GOOGLE);
        }

        response.sendRedirect("/home");

    }

}
