package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
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
public class GeoLocation {

    @Id
    private UUID id;

    @NotNull
    private Long latitude;

    @NotNull
    private Long longitude;

    @DocumentReference(lazy = true, lookup = "{ 'apartment' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Apartment apartment;

    @DocumentReference(lazy = true, lookup = "{ 'locality' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Locality locality;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

}
