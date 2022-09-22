package com.kyn.profile.repos;

import com.kyn.profile.domain.Apartment;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ApartmentRepository extends MongoRepository<Apartment, UUID> {
}
