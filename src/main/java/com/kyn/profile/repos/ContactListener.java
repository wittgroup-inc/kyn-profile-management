package com.kyn.profile.repos;

import com.kyn.profile.domain.Contact;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ContactListener extends AbstractMongoEventListener<Contact> {

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Contact> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(UUID.randomUUID());
        }
    }

}
