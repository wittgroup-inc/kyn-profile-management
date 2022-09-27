package com.kyn.profile.db.repositories;

import com.kyn.profile.db.entities.UserEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<UserEntity, UUID> {
}
