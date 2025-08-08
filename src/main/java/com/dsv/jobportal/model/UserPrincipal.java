package com.dsv.jobportal.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = user.getRoles().getName();
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Return actual password from the User
    }

    @Override
    public String getUsername() {
        return user.getEmail();  // Spring uses this as the username (usually email or username)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can change this based on account status
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Can be linked to a 'locked' field in User if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Can implement expiry logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Can link this to an 'enabled' field in User if needed
    }

    public User getUser() {
        return user;
    }
}
