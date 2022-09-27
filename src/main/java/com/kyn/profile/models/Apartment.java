package com.kyn.profile.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;


@Document
@Getter
@Setter
public class Apartment {

    @Id
    private UUID id;

    @NotNull
    @Size(max = 255)
    private String holdingNumber;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String displayPicUrl;

    private GeoLocation geoLocation;

    public static final Apartment EMPTY = new Apartment();
}
