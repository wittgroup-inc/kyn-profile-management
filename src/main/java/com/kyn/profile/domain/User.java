package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kyn.profile.model.Sex;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class User {

    @Id
    private UUID id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String profilePicUrl;

    private Sex sex;

    @DocumentReference(lazy = true)
    private Set<Address> addresses;

    @DocumentReference(lazy = true)
    private Privacy privacy;

    @DocumentReference(lazy = true)
    private Settings settings;

    @DocumentReference(lazy = true)
    private Contact contact;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

    public static final User EMPTY = new User();
}
