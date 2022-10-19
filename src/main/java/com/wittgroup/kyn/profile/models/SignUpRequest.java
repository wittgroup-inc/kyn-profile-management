package com.wittgroup.kyn.profile.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wittgroup.kyn.profile.validators.birthdate.DateOfBirth;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SignUpRequest {

    @NotNull @NonNull
    @Size(max = 255)
    private String firstName;

    @NotNull @NonNull
    @Size(max = 255)
    private String lastName;

    @NotNull @NonNull
    @DateOfBirth(message = "The birth date must be greater or equal than 15")
    @Past(message = "The date of birth must be in the past.")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;

    @NotNull @NonNull
    private Sex sex;

    @NotNull @NonNull
    @Size(max = 255)
    @Email
    private String email;

    @NotNull @NonNull
    @Size(max = 255)
    private String password;
}
