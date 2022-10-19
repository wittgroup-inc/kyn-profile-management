package com.wittgroup.kyn.profile.db.repositories;

import com.wittgroup.kyn.profile.db.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<UserEntity, UUID> {
    Optional<UserEntity> findBySettingsUsername(String user);
    Optional<UserEntity> findBySettingsPrimaryEmail(String email);
    Optional<UserEntity> findBySettingsRegisteredMobileNumber(String mobile);
}
