package com.kyn.profile.repos;

import com.kyn.profile.domain.Flat;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FlatRepository extends MongoRepository<Flat, UUID> {
}
