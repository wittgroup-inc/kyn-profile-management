package com.wittgroup.kyn.profile.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Settings {
    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String primaryEmail;

    @NotNull
    @Size(max = 255)
    private String registeredMobileNumber;

    public static final Settings EMPTY = new Settings();
}
