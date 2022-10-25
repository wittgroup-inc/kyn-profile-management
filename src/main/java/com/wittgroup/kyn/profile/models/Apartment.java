package com.wittgroup.kyn.profile.models;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Data
public class Apartment {

    private UUID id;

    @Size(max = 255)
    private String holdingNumber;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String displayPicUrl;

    private GeoLocation geoLocation;

    public static final Apartment EMPTY = new Apartment();
}
