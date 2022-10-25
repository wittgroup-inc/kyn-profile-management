package com.wittgroup.kyn.profile.db.repositories;

import com.wittgroup.kyn.profile.db.entities.ProfileEntity;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ProfileListener extends AbstractMongoEventListener<ProfileEntity> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<ProfileEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
