package com.kyn.profile.db.repositories;

import com.kyn.profile.db.entities.UserEntity;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UserListener extends AbstractMongoEventListener<UserEntity> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<UserEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
