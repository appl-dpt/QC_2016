package com.softserve.hotels.handlers;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.softserve.hotels.model.Role;

@Component
public class AuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(Role.ADMIN.toString())) {
            response.sendRedirect("admin/allUsers");
        } else if (roles.contains(Role.RENTER.toString())) {
            response.sendRedirect("renter/enabledApartments");
        } else if (roles.contains(Role.MODERATOR.toString())) {
            response.sendRedirect("moderator/enabledApartments");
        }
        if (!response.isCommitted()) {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
