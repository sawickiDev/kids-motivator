package com.steveq.kidsmotivator.app.persistence.service;

import com.steveq.kidsmotivator.app.persistence.dao.RoleRepository;
import com.steveq.kidsmotivator.app.persistence.dao.UserRepository;
import com.steveq.kidsmotivator.app.persistence.model.Role;
import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
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
        System.out.println("ENCODED PASS :: " + passwordEncoder.encode(user.getPass().getPassword()));
        String encodedPassword = passwordEncoder.encode(user.getPass().getPassword());
        user.getPass().setPassword(encodedPassword);
        user.getPass().setConfirmPassword(encodedPassword);

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
    public List<User> getKidsForParent() {
        User parent = this.getCurrentlyLoggedUser();
        List<User> parents = new ArrayList<>();
        parents.add(parent);
        System.out.println("PARENTS :: " + parents);
        List<User> kids = userRepository.findAllByParentsContains(parents);
        System.out.println("KIDS :: " + kids);

        return kids;
    }

    @Override
    public User getUserById(Integer id) {
        if (id != null)
            return userRepository.findUserById(id);

        return null;
    }

    @Override
    public boolean isUserParent(User user) {
        for (GrantedAuthority ga : user.getAuthorities()) {
            if ("PARENT".equals(ga.getAuthority()))
                return true;
        }
        return false;
    }

    @Override
    public boolean isUserKid(User user) {
        for (GrantedAuthority ga : user.getAuthorities()) {
            if ("KID".equals(ga.getAuthority()))
                return true;
        }
        return false;
    }

    @Override
    public User getCurrentlyLoggedUser() {

        String currentlyLoggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("CURRENTLY LOGGED :: " + currentlyLoggedUsername);
        User currentlyLoggedUser = (User)loadUserByUsername(currentlyLoggedUsername);
        System.out.println("CURRENTLY LOGGED :: " + currentlyLoggedUser.getAuthorities());

        return currentlyLoggedUser;
    }
}
