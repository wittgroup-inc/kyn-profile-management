package com.kyn.profile.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class Address {

    private UUID id;

    private Flat flat;

    private Apartment apartment;

    private Locality locality;

}
