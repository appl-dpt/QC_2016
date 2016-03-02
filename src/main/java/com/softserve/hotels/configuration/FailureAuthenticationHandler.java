package com.softserve.hotels.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.softserve.hotels.model.User;
import com.softserve.hotels.service.UserService;

public class FailureAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    private User user;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        user = userService.findUserByEmail(email);

        String url = (user == null) ? "/invalidCredentials"
                : !user.isEnabled() ? "/loginNotConfirmed"
                        : user.getBlocked() ? "/loginBlocked"
                                : user.getPassword().equals(password) ? "/login" : "/invalidPassword";

        this.setDefaultFailureUrl(url);
        super.onAuthenticationFailure(request, response, exception);
    }

}