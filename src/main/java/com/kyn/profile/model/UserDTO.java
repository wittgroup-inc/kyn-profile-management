package com.kyn.profile.model;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String profilePicUrl;

    private Sex sex;

    private List<UUID> address;

    private UUID privacy;

    private UUID settings;

    private UUID contact;

}
