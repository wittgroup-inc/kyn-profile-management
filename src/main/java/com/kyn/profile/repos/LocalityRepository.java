package com.kyn.profile.repos;

import com.kyn.profile.domain.Locality;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LocalityRepository extends MongoRepository<Locality, UUID> {
}
