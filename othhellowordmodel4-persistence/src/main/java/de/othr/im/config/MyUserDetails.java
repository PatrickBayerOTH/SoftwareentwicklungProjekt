package de.othr.im.config;


import de.othr.im.model.Authority;
import de.othr.im.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {


    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private boolean active;

    private List<GrantedAuthority> authorities;

    public MyUserDetails(User user) {

        this.email = user.getEmail();
        this.password = user.getPassword();
        this.active = (user.getActive() > 0) ? true : false;

        List<Authority> myauthorities = (List<Authority>) user.getMyauthorities();


        authorities = new ArrayList<>();

        for (int i = 0; i < myauthorities.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(myauthorities.get(i).getDescription().toUpperCase()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
