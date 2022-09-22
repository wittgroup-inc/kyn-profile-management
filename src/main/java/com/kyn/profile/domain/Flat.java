package com.kyn.profile.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.validation.constraints.Size;
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
public class Flat {

    @Id
    private UUID id;

    @Size(max = 255)
    private String flatNumber;

    @Size(max = 255)
    private String tower;

    @Size(max = 255)
    private String floor;

    @DocumentReference(lazy = true)
    private Apartment apartment;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

}
