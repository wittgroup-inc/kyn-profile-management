package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
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
public class Settings {

    @Id
    private UUID id;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String primaryEmail;

    @Size(max = 255)
    private String registeredMobileNumber;

    @DocumentReference(lazy = true, lookup = "{ 'user' : ?#{#self._id} }")
    @ReadOnlyProperty
    private User user;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

}
