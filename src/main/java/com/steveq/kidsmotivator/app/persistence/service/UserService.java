package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentlyLoggedUser();
    User saveUser(User user);
    List<User> getKidsForParent();
    User getUserById(Integer id);
    boolean isUserParent(User user);
    boolean isUserKid(User user);
}
