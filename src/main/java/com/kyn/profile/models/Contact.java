package com.kyn.profile.models;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


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
