package com.kyn.profile.repos;

import com.kyn.profile.domain.Settings;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class SettingsListener extends AbstractMongoEventListener<Settings> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Settings> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
