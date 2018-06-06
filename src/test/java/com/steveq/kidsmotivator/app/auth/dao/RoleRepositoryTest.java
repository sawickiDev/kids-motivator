package com.steveq.kidsmotivator.app.auth.dao;

import com.steveq.kidsmotivator.app.auth.model.Role;
import com.steveq.kidsmotivator.factory.UserDataFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() throws Exception {
        Role role = UserDataFactory.createSimpleRole("PARENT");
        entityManager.persist(role);
    }

    @Test
    public void findRoleByRole() {
        Role role = roleRepository.findRoleByRole("PARENT");
        assertEquals("PARENT", role.getRole());
    }

    @Test
    public void findRoleByRoleNotExistingRole() {
        Role role = roleRepository.findRoleByRole("UNKNOWN");
        assertNull(role);
    }
}