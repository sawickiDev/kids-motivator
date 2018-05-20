package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.dao.UserRepository;
import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("USERNAME :: " + s);
//        String[] names = s.trim().split(" ");
        User user = null;

        if (s.length() > 3)
            user = userRepository.findByUserName(s);

        if (user == null)
            throw new IllegalArgumentException("PROVIDED USERNAME IS INCORRECT");

        return user;
    }
}
