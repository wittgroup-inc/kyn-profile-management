package com.kyn.profile.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class GeoLocation {

    @NotNull
    private Long latitude;

    @NotNull
    private Long longitude;

    public static final GeoLocation EMPTY = new GeoLocation();
}
