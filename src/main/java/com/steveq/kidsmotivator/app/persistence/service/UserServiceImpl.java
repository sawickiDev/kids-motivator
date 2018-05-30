package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.dao.RoleRepository;
import com.steveq.kidsmotivator.app.persistence.dao.UserRepository;
import com.steveq.kidsmotivator.app.persistence.model.Role;
import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    public User saveUser(User user) {
        User persistedUser = null;
        User parentUser = this.getCurrentlyLoggedUser();

        Role kidRole = roleRepository.findRoleByRole("KID");
        user.addRole(kidRole);
        user.addParent(parentUser);

        try{
            persistedUser = userRepository.save(user);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        if(persistedUser != null) {
            parentUser.addChildren(persistedUser);
            userRepository.save(parentUser);
        }

        return persistedUser;
    }

    @Override
    public User getCurrentlyLoggedUser() {

        String currentlyLoggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("authorities : " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        User currentlyLoggedUser = (User)loadUserByUsername(currentlyLoggedUsername);

        return currentlyLoggedUser;
    }
}
