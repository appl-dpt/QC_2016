package com.softserve.hotels.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.softserve.hotels.dao.UserDao;
import com.softserve.hotels.social.CustomUserDetails;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login) {
        com.softserve.hotels.model.User domainUser;
        domainUser = userDao.findUserByEmail(login);
        CustomUserDetails principal = CustomUserDetails.getBuilder().id(domainUser.getId())
                .firstName(domainUser.getFirstname()).lastName(domainUser.getLastname())
                .username(domainUser.getNickname()).password(domainUser.getPassword())
                .phoneNumber(domainUser.getPhonenumber()).role(domainUser.getRole()).email(domainUser.getEmail())
                .build();
        if (domainUser.isEnabled() && !domainUser.getBlocked()) {
            return principal;
        }
        return null;
    }

    public Collection<GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
