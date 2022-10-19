package com.wittgroup.kyn.profile.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;


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
    @Indexed(unique = true)
    private String registeredMobileNumber;

}
