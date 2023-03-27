package com.gowittgroup.kyn.profile.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class Privacy {

    @NotNull @NonNull
    private Relation whoCanChat;

    @NotNull @NonNull
    private Relation whoCanContact;

    @NotNull @NonNull
    private Relation whoCanSeeAddress;

    @NotNull @NonNull
    private Relation whoCanSearch;

    @NotNull @NonNull
    private Relation whoCanSendFriendRequest;

    @NotNull @NonNull
    private Relation whoCanAddInGroup;

    public static final Privacy DEFAULT = new Privacy(Relation.FRIEND, Relation.FRIEND_OF_FRIEND, Relation.ONLY_ME, Relation.ANYONE, Relation.FRIEND_OF_FRIEND, Relation.FRIEND);
}
