package com.kyn.profile.repos;

import com.kyn.profile.domain.User;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, UUID> {
}
