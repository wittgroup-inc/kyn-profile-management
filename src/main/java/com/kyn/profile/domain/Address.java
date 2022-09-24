package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
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
public class Address {

    @Id
    private UUID id;

    @DocumentReference(lazy = true, lookup = "{ 'users' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<User> users;

    @DocumentReference(lazy = true)
    private Apartment apartment;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

    public static final Address EMPTY = new Address();
}
