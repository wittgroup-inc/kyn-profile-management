package com.kyn.profile.repos;

import com.kyn.profile.domain.Locality;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class LocalityListener extends AbstractMongoEventListener<Locality> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Locality> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
