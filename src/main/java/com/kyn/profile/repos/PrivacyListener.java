package com.kyn.profile.repos;

import com.kyn.profile.domain.Privacy;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class PrivacyListener extends AbstractMongoEventListener<Privacy> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Privacy> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
