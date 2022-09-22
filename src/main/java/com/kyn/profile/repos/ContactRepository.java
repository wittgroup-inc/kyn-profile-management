package com.kyn.profile.repos;

import com.kyn.profile.domain.Contact;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ContactRepository extends MongoRepository<Contact, UUID> {
}
