package com.kyn.profile.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kyn.profile.domain.Address;
import com.kyn.profile.domain.Contact;
import com.kyn.profile.domain.Privacy;
import com.kyn.profile.domain.Settings;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

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
