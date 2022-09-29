package com.wittgroup.kyn.profile.models;

import lombok.Getter;
import lombok.Setter;


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
