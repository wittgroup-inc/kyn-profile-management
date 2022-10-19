package com.wittgroup.kyn.profile.validators.birthdate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface DateOfBirth {
    String message() default "Invalid date of birth.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
