package com.steveq.kidsmotivator.app.auth.model;

import com.steveq.kidsmotivator.factory.UserDataFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordTest {

    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Before
    public void setUp() throws Exception {
        localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.afterPropertiesSet();

    }

    @Test
    public void passwordInitializedWithoutCorrectConfirmation() {
        Password password = UserDataFactory.createSimplePassword("testPass", "test");
        Set<ConstraintViolation<Password>> constraintViolations = localValidatorFactoryBean.validate(password);
        assertTrue(
            constraintViolations
                .stream()
                .anyMatch(cv -> "Password and Confirm Password must match".equals(cv.getMessageTemplate()))
        );
    }

    @Test
    public void passwordInitializedTooLittlePassLetters() {
        Password password = UserDataFactory.createSimplePassword("te", "test");
        Set<ConstraintViolation<Password>> constraintViolations = localValidatorFactoryBean.validate(password);
        assertTrue(
                constraintViolations
                        .stream()
                        .anyMatch(cv -> "size must be between 3 and 30".equals(cv.getMessage()))
        );
    }

    @Test
    public void passwordInitializedTooLittleConfirmPassLetters() {
        Password password = UserDataFactory.createSimplePassword("test", "te");
        Set<ConstraintViolation<Password>> constraintViolations = localValidatorFactoryBean.validate(password);
        assertTrue(
                constraintViolations
                        .stream()
                        .anyMatch(cv -> "size must be between 3 and 30".equals(cv.getMessage()))
        );
    }

    @Test
    public void passwordInitializationIdValue() {
        Password password = UserDataFactory.createSimplePassword("test", "te");
        password.setId(12);
        assertEquals(12, password.getId());
    }

    @Test
    public void passwordInitializationIdValueIncorrect() {
        Password password = UserDataFactory.createSimplePassword("test", "te");
        password.setId(-1);
        assertEquals(0, password.getId());
    }

    @Test
    public void passwordToString() {
        Password password = UserDataFactory.createSimplePassword("test", "te");
        String passString = password.toString();
        assertTrue(!passString.isEmpty());
    }
}