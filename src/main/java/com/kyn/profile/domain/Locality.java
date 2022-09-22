package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class Locality {

    @Id
    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String city;

    @NotNull
    @Size(max = 255)
    private String pinCode;

    @DocumentReference(lazy = true, lookup = "{ 'apartments' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Apartment> apartments;

    @DocumentReference(lazy = true)
    private GeoLocation geoLocation;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

}
