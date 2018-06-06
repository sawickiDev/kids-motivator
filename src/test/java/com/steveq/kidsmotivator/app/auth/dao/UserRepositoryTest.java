package com.steveq.kidsmotivator.app.auth.dao;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.factory.UserDataFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception
    {

        User kid = UserDataFactory.createSimpleUser("kid1", "test", "KID");
        entityManager.persist(kid);

        User parent = UserDataFactory.createSimpleUser("parent1", "test", "PARENT");
        parent.addChildren(kid);
        entityManager.persist(parent);

        kid = UserDataFactory.createSimpleUser("kid2", "test", "KID");
        entityManager.persist(kid);

        parent = UserDataFactory.createSimpleUser("parent2", "test", "PARENT");
        parent.addChildren(kid);
        entityManager.persist(parent);
        entityManager.flush();
    }

    @Test
    public void findByUserNameCorrect()
    {
        User user = userRepository.findByUserName("parent1test");
        assertEquals("parent1", user.getFirstName());
    }

    @Test
    public void findByUserNameNotExisting()
    {
        User user = userRepository.findByUserName("unknown");
        assertNull(user);
    }

    @Test
    public void findAllByParentsContains()
    {
        List<User> users = userRepository.findAllByParentsContains(Stream.of(userRepository.findByUserName("parent1test")).collect(Collectors.toList()));
        assertEquals(1, users.size());
        assertEquals("kid1", users.get(0).getFirstName());
    }

    @Test
    public void findAllByParentsContainsAndActiveIsTrue() {
        User kid = userRepository.findByUserName("kid1test");
        kid.setActive(false);
        entityManager.persist(kid);
        List<User> users = userRepository.findAllByParentsContainsAndActiveIsTrue(Stream.of(userRepository.findByUserName("parent1test")).collect(Collectors.toList()));
        assertEquals(0, users.size());
    }

    @Test
    public void findUserById() {
        User kid = userRepository.findByUserName("kid2test");
        User user = userRepository.findUserById(kid.getId());
        assertEquals("kid2", user.getFirstName());
    }
}