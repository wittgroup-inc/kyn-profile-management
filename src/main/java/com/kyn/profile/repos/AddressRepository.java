package com.kyn.profile.repos;

import com.kyn.profile.domain.Address;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AddressRepository extends MongoRepository<Address, UUID> {
}
