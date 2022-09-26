package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.kyn.profile.model.Relation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Getter
@Setter
public class Privacy {

    private Relation whoCanChat;

    private Relation whoCanContact;

    private Relation whoCanSeeAddress;

    private Relation whoCanSearch;

    private Relation whoCanSendFriendRequest;

    private Relation whoCanAddInGroup;

    public static final Privacy EMPTY = new Privacy();
}
