package com.wittgroup.kyn.profile.models;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String profilePicUrl;

    private Sex sex;

    private List<Address> address;

    private Privacy privacy;

    private Settings settings;

    private Contact contact;

}
