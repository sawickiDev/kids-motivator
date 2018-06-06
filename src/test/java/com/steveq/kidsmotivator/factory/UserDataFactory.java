package com.steveq.kidsmotivator.factory;

import com.steveq.kidsmotivator.app.auth.model.Password;
import com.steveq.kidsmotivator.app.auth.model.Role;
import com.steveq.kidsmotivator.app.auth.model.User;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDataFactory {
    public static User createSimpleUser(String firstName, String lastName, String roleName) {
        Password password = createSimplePassword(firstName + lastName, firstName + lastName);

        Role role = createSimpleRole(roleName);

        User user = new User();
        user.setActive(true);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(firstName + lastName);
        user.setRoles(Stream.of(role).collect(Collectors.toSet()));
        user.setPass(password);
        user.setMissions(new HashSet<>());
        user.setOwnedMissions(new HashSet<>());
        user.setOwnedPrizes(new HashSet<>());
        user.setTakenPrizes(new HashSet<>());

        return user;
    }

    public static Password createSimplePassword(String pass, String confirmPass) {
        Password password = new Password();
        password.setPassword(pass);
        password.setConfirmPassword(confirmPass);

        return password;
    }

    public static Role createSimpleRole(String roleName) {
        Role role = new Role();
        role.setRole(roleName);

        return role;
    }
}
