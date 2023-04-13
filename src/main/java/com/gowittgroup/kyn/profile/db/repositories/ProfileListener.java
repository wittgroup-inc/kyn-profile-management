package com.gowittgroup.kyn.profile.db.repositories;

import com.gowittgroup.kyn.profile.db.entities.ProfileEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ProfileListener extends AbstractMongoEventListener<ProfileEntity> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<ProfileEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
