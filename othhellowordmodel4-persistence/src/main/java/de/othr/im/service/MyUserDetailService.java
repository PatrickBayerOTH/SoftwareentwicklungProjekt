package de.othr.im.service;

import java.util.Optional;

import de.othr.im.config.MyUserDetails;
import de.othr.im.model.User;
import de.othr.im.model.oauthUser.CustomOAuth2User;
import de.othr.im.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        String email = username;

        Optional<User> oUser = userRepository.findUserByEmail(username);
        oUser.orElseThrow(() -> new UsernameNotFoundException("Not found" + email));
        System.out.println("User found at the UserDetailService = " + oUser.get().getEmail());

        return new MyUserDetails(oUser.get());

    }
}
