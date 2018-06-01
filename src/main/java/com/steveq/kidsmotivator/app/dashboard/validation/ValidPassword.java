package com.steveq.kidsmotivator.app.dashboard.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "Password and Confirm Password must match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
