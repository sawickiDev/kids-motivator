package com.steveq.kidsmotivator.config;

import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> auths = authentication.getAuthorities();

        for (GrantedAuthority ga : auths) {
            if ("PARENT".equals(ga.getAuthority()))
                httpServletResponse.sendRedirect(httpServletResponse.encodeURL("dashboard"));
            else if ("KID".equals(ga.getAuthority()))
                httpServletResponse.sendRedirect(httpServletResponse.encodeURL("missions"));

        }
    }
}
