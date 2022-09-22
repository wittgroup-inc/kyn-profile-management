package com.kyn.profile.domain;

import java.time.OffsetDateTime;
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
public class Privacy {

    @Id
    private UUID id;

    private Integer whoCanChat;

    private Integer whoCanContact;

    private Integer whoCanSeeAddress;

    private Integer whoCanSearch;

    private Integer whoCanSendFriendRequest;

    private Integer whoCanAddInGroup;

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
