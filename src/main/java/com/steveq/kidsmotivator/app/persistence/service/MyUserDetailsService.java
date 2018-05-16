package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.dao.UserRepository;
import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] names = s.trim().split(" ");
        User user = null;

        if (names.length > 0 && names.length < 3)
            user = userRepository.findByFirstNameAndLastName(names[0], names[1]);

        if (user == null)
            throw new IllegalArgumentException("PROVIDED USERNAME IS INCORRECT");

        return user;
    }
}
