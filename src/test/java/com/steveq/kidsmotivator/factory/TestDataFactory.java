package com.steveq.kidsmotivator.factory;

import com.steveq.kidsmotivator.app.auth.model.Password;
import com.steveq.kidsmotivator.app.auth.model.Role;
import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.prizes.model.Prize;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataFactory {
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

    public static Mission createSimpleMission(String title, String description, Mission.STAGE stage, int value, String dateFormat) {
        Mission mission = new Mission();
        mission.setTitle(title);
        mission.setDescription(description);
        mission.setStage(stage.name());
        mission.setValue(value);
        mission.setDateFormat(dateFormat);

        return mission;
    }

    public static Prize createSimplePrize(String name, int value) {
        Prize prize = new Prize();
        prize.setName(name);
        prize.setValue(value);

        return prize;
    }
}
