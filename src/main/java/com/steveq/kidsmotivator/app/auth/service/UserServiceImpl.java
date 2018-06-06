package com.steveq.kidsmotivator.app.auth.service;

import com.steveq.kidsmotivator.app.auth.dao.RoleRepository;
import com.steveq.kidsmotivator.app.auth.dao.UserRepository;
import com.steveq.kidsmotivator.app.auth.model.Role;
import com.steveq.kidsmotivator.app.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;

        if (s != null)
            user = userRepository.findByUserName(s);

        if (user == null)
            throw new IllegalArgumentException("PROVIDED USERNAME IS INCORRECT");

        return user;
    }

    @Override
    public User saveUser(User user) {
        User persistedUser = null;
        User parentUser = this.getCurrentlyLoggedUser();

        // set default role - KID
        // set parent
        Role kidRole = roleRepository.findRoleByRole("KID");
        user.addRole(kidRole);
        user.addParent(parentUser);

        // encode password before save
        String encodedPassword = passwordEncoder.encode(user.getPass().getPassword());
        user.getPass().setPassword(encodedPassword);
        user.getPass().setConfirmPassword(encodedPassword);

        // insert contact operation
        try{
            persistedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException dive){
            dive.printStackTrace();
        }

        // update parent's children relation
        if(persistedUser != null) {
            parentUser.addChildren(persistedUser);
            userRepository.save(parentUser);
        }

        return persistedUser;
    }

    @Override
    public List<User> getKidsForUser(User user) {
        List<User> kids = new ArrayList<>();

        if (this.isUserParent(user))
            kids = userRepository.findAllByParentsContains(Stream.of(user).collect(Collectors.toList()));

        return kids;
    }

    @Override
    public List<User> getActiveKidsForUser(User user) {
        List<User> kids = new ArrayList<>();

        if (this.isUserParent(user))
            kids = userRepository.findAllByParentsContainsAndActiveIsTrue(Stream.of(user).collect(Collectors.toList()));

        return kids;
    }

    @Override
    public User getUserById(Integer id) {
        if (id != null)
            return userRepository.findUserById(id);

        return new User();
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
        return (User)loadUserByUsername(currentlyLoggedUsername);
    }
}
