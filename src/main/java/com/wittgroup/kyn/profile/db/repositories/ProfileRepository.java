package com.wittgroup.kyn.profile.db.repositories;

import com.wittgroup.kyn.profile.db.entities.ProfileEntity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findBySettingsUsername(String user);

    Optional<ProfileEntity> findBySettingsPrimaryEmail(String email);

    Optional<ProfileEntity> findBySettingsRegisteredMobileNumber(String mobile);

    Optional<ProfileEntity> findBySettingsUsernameOrSettingsPrimaryEmailOrSettingsRegisteredMobileNumber(String username, String email, String mobileNumber);

    Optional<ProfileEntity> findBySettingsPrimaryEmailOrSettingsRegisteredMobileNumber(String email, String mobileNumber);

    Optional<ProfileEntity> findBySettingsUsernameOrSettingsPrimaryEmail(String username, String email);
}
