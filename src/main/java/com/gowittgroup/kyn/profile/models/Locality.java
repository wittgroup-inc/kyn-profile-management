package com.gowittgroup.kyn.profile.models;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Data
public class Locality {

    private UUID id;

    @NotNull @NonNull
    @Size(max = 255)
    private String name;

    @NotNull @NonNull
    @Size(max = 255)
    private String city;

    @NotNull @NonNull
    @Size(max = 255)
    private String pinCode;

    private GeoLocation geoLocation;

}
