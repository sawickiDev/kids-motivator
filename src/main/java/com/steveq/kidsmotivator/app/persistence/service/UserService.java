package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentlyLoggedUser();
    User saveUser(User user);
}
