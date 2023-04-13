package com.gowittgroup.kyn.profile.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class Settings {

    @NonNull @NotNull
    @Size(max = 255)
    @Indexed(unique = true)
    private String username;

    @NotNull @NonNull
    @Size(max = 255)
    private String password;

    @NotNull @NonNull
    @Size(max = 255)
    @Indexed(unique = true)
    private String primaryEmail;

    @Size(max = 255)
    private String registeredMobileNumber;

}
