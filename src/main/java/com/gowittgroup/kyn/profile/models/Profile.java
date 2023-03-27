package com.gowittgroup.kyn.profile.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowittgroup.kyn.profile.validators.birthdate.DateOfBirth;
import lombok.Data;
import lombok.NonNull;


@Data
public class Profile {

    private UUID id;

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

    @Size(max = 255)
    private String profilePicUrl;

    @NotNull @NonNull
    private Sex sex;

    private List<Address> address;

    @NotNull @NonNull
    private Privacy privacy;

    @NotNull @NonNull
    private Settings settings;

    private Contact contact;

}
