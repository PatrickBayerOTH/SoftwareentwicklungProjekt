package de.othr.im.config;


import de.othr.im.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    MyUserDetailService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/home").permitAll() //all users can access this page
                .antMatchers("/login").permitAll() //all users can access this page
                .antMatchers("/user/student/add").permitAll()
                .antMatchers("/user/student/add/process").permitAll()
                //more permissions here....
                .antMatchers("/admin/**", "/users/**", "/settings/**").hasAuthority("ADMIN")
                .antMatchers("/studentProfessor/**", "/settings/**").hasAuthority("STUDENT")
                //.antMatchers("/professor/**", "/settings/**").hasAuthority("PROFESSOR")


                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .and().antMatcher("user/student/add")
                .antMatcher("user/student/add/process")
                .logout().logoutSuccessUrl("/prelogout")
                .invalidateHttpSession(true)

                .permitAll();


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
    public PasswordEncoder getPasswordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }


}
