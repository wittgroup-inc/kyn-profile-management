package com.kyn.profile.repos;

import com.kyn.profile.domain.Privacy;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PrivacyRepository extends MongoRepository<Privacy, UUID> {
}
