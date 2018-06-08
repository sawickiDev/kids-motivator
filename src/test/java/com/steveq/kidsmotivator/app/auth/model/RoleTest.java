package com.steveq.kidsmotivator.app.auth.model;

import com.steveq.kidsmotivator.factory.TestDataFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Before
    public void setUp() throws Exception {
        localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.afterPropertiesSet();

    }

    @Test
    public void roleInitializationWithShortName() {
        Role role = TestDataFactory.createSimpleRole("te");
        Set<ConstraintViolation<Role>> constraintViolations = localValidatorFactoryBean.validate(role);
        assertTrue(
                constraintViolations
                        .stream()
                        .anyMatch(cv -> "size must be between 3 and 20".equals(cv.getMessage()))
        );
    }

    @Test
    public void roleInitializationCorrect() {
        Role role = TestDataFactory.createSimpleRole("testr");
        Set<ConstraintViolation<Role>> constraintViolations = localValidatorFactoryBean.validate(role);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void roleToString() {
        Role role = TestDataFactory.createSimpleRole("testr");
        String roleString = role.toString();
        assertTrue(!roleString.isEmpty());
    }


    @Test
    public void roleInitializationIdValue() {
        Role role = TestDataFactory.createSimpleRole("testr");
        role.setId(12);
        assertEquals(12, role.getId());
    }

    @Test
    public void roleInitializationIdValueIncorrect() {
        Role role = TestDataFactory.createSimpleRole("testr");
        role.setId(-2);
        assertEquals(-1, role.getId());
    }

    @Test
    public void roleGetRole() {
        Role role = TestDataFactory.createSimpleRole("testr");
        assertEquals("testr", role.getRole());
    }
}