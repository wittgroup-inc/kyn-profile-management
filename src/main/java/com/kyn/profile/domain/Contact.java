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


@Getter
@Setter
public class Contact {

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String mobileNumber;

    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String facebookProfileLink;

    @Size(max = 255)
    private String twitterProfileLink;

    @Size(max = 255)
    private String instagramProfileLink;

    @Size(max = 255)
    private String linkedinProfileLink;

    @Size(max = 255)
    private String portfolioLink;

    @Size(max = 255)
    private String blogLink;

    public static final Contact EMPTY = new Contact();

}
