package de.othr.im.config;


import de.othr.im.model.oauthUser.CustomOAuth2UserService;
import de.othr.im.model.oauthUser.OAuth2LoginSuccessHandler;
import de.othr.im.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {



    @Autowired
    MyUserDetailService userDetailsService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()//all users can access this page
                .antMatchers("/user/**").permitAll()//all users can access this page
                .antMatchers("/admin/**", "/settings/**").hasAuthority("ADMIN")
                //.antMatchers("/student/**", "/settings/**").hasAuthority("STUDENT")
                //.antMatchers("/professor/**", "/settings/**").hasAuthority("PROFESSOR")


                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService).and()
                .successHandler(oAuth2LoginSuccessHandler)
                .and().logout()
                .logoutSuccessUrl("/login")
                .logoutUrl("/prelogout")
                .invalidateHttpSession(true)
                .permitAll();//.and().exceptionHandling().accessDeniedPage("/403");


        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
