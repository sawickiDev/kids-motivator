package com.steveq.kidsmotivator.app.auth.service;

import com.steveq.kidsmotivator.app.auth.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentlyLoggedUser();
    User saveUser(User user);
    List<User> getKidsForUser(User user);
    List<User> getActiveKidsForUser(User user);
    User getUserById(Integer id);
    boolean isUserParent(User user);
    boolean isUserKid(User user);
}
