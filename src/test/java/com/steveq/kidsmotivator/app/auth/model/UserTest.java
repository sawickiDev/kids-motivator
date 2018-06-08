package com.steveq.kidsmotivator.app.auth.model;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import com.steveq.kidsmotivator.factory.TestDataFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Before
    public void setUp() throws Exception {
        localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.afterPropertiesSet();
    }

    @Test
    public void userInitializeWithConstraintsFulfilled() {
        User user = TestDataFactory.createSimpleUser("test", "user", "role");

        Set<ConstraintViolation<User>> constraintViolations = localValidatorFactoryBean.validate(user);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void userInitializeWithoutConstraintsFulfilled() {
        Password password = TestDataFactory.createSimplePassword("te", "tre");
        User user = TestDataFactory.createSimpleUser("us", "te", "r");
        user.setPass(password);

        Set<ConstraintViolation<User>> constraintViolations = localValidatorFactoryBean.validate(user);
        assertTrue(
            constraintViolations
                    .stream()
                    .anyMatch(cv -> "Password and Confirm Password must match".equals(cv.getMessage()))
        );
        assertTrue(
            constraintViolations
                    .stream()
                    .anyMatch(cv -> "size must be between 3 and 30".equals(cv.getMessage()))
        );
    }

    @Test
    public void userWithRelation() {
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        User kid = TestDataFactory.createSimpleUser("kid", "user", "kid");
        parent.addChildren(kid);
        kid.addParent(parent);

        assertEquals(1, parent.getChildren().size());
        assertEquals(1, kid.getParents().size());
    }

    @Test
    public void userMissionsBalance() {
        Mission mission = TestDataFactory.createSimpleMission("mission", "mission", Mission.STAGE.ASSIGNED, 100, "2018-02-01");
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        parent.addMission(mission);
        assertEquals(1, parent.getSumMissions());
    }

    @Test
    public void userMissionsCount() {
        Mission mission1 = TestDataFactory.createSimpleMission("mission1", "mission1", Mission.STAGE.ASSIGNED, 100, "2018-02-01");
        Mission mission2 = TestDataFactory.createSimpleMission("mission2", "mission2", Mission.STAGE.ASSIGNED, 100, "2018-02-01");
        User parent = TestDataFactory.createSimpleUser("kid", "user", "parent");
        parent.addMission(mission1);
        parent.addMission(mission2);
        assertEquals(2, parent.getMissions().size());
    }

    @Test
    public void userOwnedMissionsCount() {
        Mission mission1 = TestDataFactory.createSimpleMission("mission1", "mission1", Mission.STAGE.ASSIGNED, 100, "2018-02-01");
        Mission mission2 = TestDataFactory.createSimpleMission("mission2", "mission2", Mission.STAGE.ASSIGNED, 100, "2018-02-01");
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        parent.setOwnedMissions(Arrays.asList(new Mission[]{mission1, mission2}).stream().collect(Collectors.toSet()));
        assertEquals(2, parent.getOwnedMissions().size());
    }

    @Test
    public void userOwnedPrizesCount() {
        Prize prize1 = TestDataFactory.createSimplePrize("prize1",100);
        Prize prize2 = TestDataFactory.createSimplePrize("prize2", 200);
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        parent.setOwnedPrizes(Arrays.asList(new Prize[]{prize1, prize2}).stream().collect(Collectors.toSet()));
        assertEquals(2, parent.getOwnedPrizes().size());
    }

    @Test
    public void userPointsBalanceWithoutPrizesTaken() {
        Prize prize = TestDataFactory.createSimplePrize("prize", 100);
        Mission mission = TestDataFactory.createSimpleMission("mission", "mission", Mission.STAGE.DONE, 100, "2018-02-01");
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        parent.addMission(mission);
        parent.addTakenPrize(prize);
        assertEquals(0, parent.getSumPoints());
    }

    @Test
    public void userToString() {
        User user = TestDataFactory.createSimpleUser("parent", "user", "parent");
        assertTrue(!user.toString().isEmpty());
    }

    @Test
    public void userDetailsInterfaceMethods() {
        User user = TestDataFactory.createSimpleUser("parent", "user", "parent");
        assertEquals(1, user.getAuthorities().size());
        assertEquals("parent", user.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority());
        assertEquals("parentuser", user.getPassword());
        assertEquals("parentuser", user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    public void userCheckParams() {
        User parent = TestDataFactory.createSimpleUser("parent", "user", "parent");
        parent.setId(1);
        assertEquals(1, parent.getId());
        assertEquals("parent", parent.getFirstName());
        assertEquals("user", parent.getLastName());
        assertEquals(true, parent.getActive());
        assertEquals("parentuser", parent.getPass().getPassword());
        assertEquals("parentuser", parent.getUserName());
        assertTrue(parent.getRoles().stream().anyMatch(s -> "parent".equals(s.getRole())));
        assertEquals(true, parent.getActive());
    }

    @Test
    public void userGetTakenPrizes() {
        User kid = TestDataFactory.createSimpleUser("kid", "user", "kid");
        Prize prize1 = TestDataFactory.createSimplePrize("prize1", 200);
        Prize prize2 = TestDataFactory.createSimplePrize("prize2", 300);
        kid.addTakenPrize(prize1);
        kid.addTakenPrize(prize2);

        assertEquals(2, kid.getTakenPrizes().size());
    }

    @Test
    public void userAddingRole() {
        User kid = TestDataFactory.createSimpleUser("kid", "user", "kid");
        Role role1 = TestDataFactory.createSimpleRole("role1");
        Role role2 = TestDataFactory.createSimpleRole("role2");
        kid.addRole(role1);
        kid.addRole(role2);

        assertEquals(3, kid.getRoles().size());
    }
}