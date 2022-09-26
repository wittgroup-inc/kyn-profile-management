package com.kyn.profile.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class Flat {

    @Size(max = 255)
    private String flatNumber;

    @Size(max = 255)
    private String tower;

    @Size(max = 255)
    private String floor;

    public static final Flat EMPTY = new Flat();

}
