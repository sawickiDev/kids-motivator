package com.steveq.kidsmotivator.app.auth.service;

import com.steveq.kidsmotivator.app.auth.dao.RoleRepository;
import com.steveq.kidsmotivator.app.auth.dao.UserRepository;
import com.steveq.kidsmotivator.app.auth.model.Password;
import com.steveq.kidsmotivator.app.auth.model.Role;
import com.steveq.kidsmotivator.app.auth.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User sampleCorrectUser;

    @Before
    public void setUp() throws Exception {
        Role sampleRole = new Role();
        sampleRole.setId(1);
        sampleRole.setRole("PARENT");

        Password password = new Password();
        password.setPassword("atest");
        password.setConfirmPassword("atest");

        sampleCorrectUser = new User();
        sampleCorrectUser.setId(1);
        sampleCorrectUser.setActive(true);
        sampleCorrectUser.setFirstName("test");
        sampleCorrectUser.setLastName("user");
        sampleCorrectUser.setUserName("testUser");
        sampleCorrectUser.setRoles(Stream.of(sampleRole).collect(Collectors.toSet()));
        sampleCorrectUser.setPass(password);
        sampleCorrectUser.setMissions(new HashSet<>());
        sampleCorrectUser.setOwnedMissions(new HashSet<>());
        sampleCorrectUser.setOwnedPrizes(new HashSet<>());
        sampleCorrectUser.setTakenPrizes(new HashSet<>());

        given(this.userRepository.findByUserName(anyString())).willReturn(sampleCorrectUser);
    }

    @Test
    public void loadUserByUsernameCorrectInput() {
        User user = (User) this.userService.loadUserByUsername("testUsername");
        assertEquals(1, user.getId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void loadUserByUsernameNullInput() {
        User user = (User) userService.loadUserByUsername(null);
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"PARENT"})
    public void saveUser() {

        Password password = new Password();
        password.setPassword("atest");
        password.setConfirmPassword("atest");

        User userToSave = new User();
        userToSave.setFirstName("Adam");
        userToSave.setLastName("Test");
        userToSave.setUserName("atest");
        userToSave.setPass(password);

        given(this.userRepository.save(any(User.class))).willReturn(userToSave);

        this.userService.saveUser(userToSave);
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"PARENT"})
    public void saveUserWithException() {

        Password password = new Password();
        password.setPassword("atest");
        password.setConfirmPassword("atest");

        User userToSave = new User();
        userToSave.setFirstName("Adam");
        userToSave.setLastName("Test");
        userToSave.setUserName("atest");
        userToSave.setPass(password);

        given(this.userRepository.save(any(User.class))).willThrow(DataIntegrityViolationException.class);

        User savedUser = this.userService.saveUser(userToSave);
        assertNull(savedUser);
    }

    @Test
    public void getKidsForUser() {
        given(this.userRepository.findAllByParentsContains(anyList()))
                .willReturn(Stream.of(sampleCorrectUser).collect(Collectors.toList()));

        List<User> kids = this.userService.getKidsForUser(sampleCorrectUser);
        assertEquals("testUser", kids.get(0).getUserName());
    }

    @Test
    public void getActiveKidsForUser() {
        given(this.userRepository.findAllByParentsContainsAndActiveIsTrue(anyList()))
                .willReturn(Stream.of(sampleCorrectUser).collect(Collectors.toList()));

        List<User> kidsForUser = this.userService.getActiveKidsForUser(sampleCorrectUser);

        assertEquals(1, kidsForUser.size());
        assertEquals("testUser", kidsForUser.get(0).getUserName());
    }

    @Test
    public void getUserById() {
        given(this.userRepository.findUserById(anyInt()))
                .willReturn(sampleCorrectUser);

        User user = this.userService.getUserById(1);
        assertEquals("testUser", user.getUserName());
    }

    @Test
    public void getUserByIdWithPassedNull() {
        User user = this.userService.getUserById(null);
        assertNull(user.getUserName());
    }

    @Test
    public void isUserParentWhenParent() {
        assertTrue(this.userService.isUserParent(sampleCorrectUser));
    }

    @Test
    public void isUserParentWhenKid() {
        Role kidRole = new Role();
        kidRole.setId(2);
        kidRole.setRole("KID");
        sampleCorrectUser.setRoles(Stream.of(kidRole).collect(Collectors.toSet()));
        assertFalse(this.userService.isUserParent(sampleCorrectUser));
    }

    @Test
    public void isUserKidWhenKid() {
        Role kidRole = new Role();
        kidRole.setId(2);
        kidRole.setRole("KID");
        sampleCorrectUser.setRoles(Stream.of(kidRole).collect(Collectors.toSet()));
        assertTrue(this.userService.isUserKid(sampleCorrectUser));
    }

    @Test
    public void isUserKidWhenParent() {
        assertFalse(this.userService.isUserKid(sampleCorrectUser));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void getCurrentlyLoggedUser() {
        User loggedUser = this.userService.getCurrentlyLoggedUser();
        assertEquals("testUser", loggedUser.getUserName());
    }
}