package com.gowittgroup.kyn.profile.db.entities;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gowittgroup.kyn.profile.models.Sex;
import com.gowittgroup.kyn.profile.models.Contact;
import com.gowittgroup.kyn.profile.models.Privacy;
import com.gowittgroup.kyn.profile.models.Settings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("profile")
@Getter
@Setter
public class ProfileEntity {

    @Id
    private java.util.UUID id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    private Date dob;

    @Size(max = 255)
    private String profilePicUrl;

    @NotNull
    private Sex sex;

    private List<UUID> addresses;

    @NotNull
    private Privacy privacy;

    @NotNull
    private Settings settings;

    private Contact contact;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

    public static final ProfileEntity EMPTY = new ProfileEntity();
}
