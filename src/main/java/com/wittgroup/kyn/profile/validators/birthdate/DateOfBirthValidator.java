package com.wittgroup.kyn.profile.validators.birthdate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(value);

        return Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 15;
    }
}
