package com.softserve.hotels.social;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;
import com.softserve.hotels.service.UserService;

public class AccountConnectionSignUp implements ConnectionSignUp {
    private UserService userService;

    public AccountConnectionSignUp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        User user = new User();
        String nickname = connection.getDisplayName();
        user.setNickname(nickname);

        String email = userProfile.getEmail();

        if (email == null) {
            email = nickname + "@" + connection.createData().getProviderId();
        }
        user.setEmail(email);
        user.setFirstname(userProfile.getFirstName());
        user.setLastname(userProfile.getLastName());
        user.setPassword(RandomStringUtils.random(User.PASSWORD_MAX_SIZE));
        user.setImageLink(connection.getImageUrl());
        user.setRole(Role.USER);
        user.setEnabled(true);
        if (userService.findUserByEmail(email) == null) {
            userService.create(user);
        }
        return user.getEmail();
    }

}
