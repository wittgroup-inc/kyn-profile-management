package com.kyn.profile.repos;

import com.kyn.profile.domain.GeoLocation;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface GeoLocationRepository extends MongoRepository<GeoLocation, UUID> {
}
