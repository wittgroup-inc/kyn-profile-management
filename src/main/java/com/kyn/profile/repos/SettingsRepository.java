package com.kyn.profile.repos;

import com.kyn.profile.domain.Settings;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SettingsRepository extends MongoRepository<Settings, UUID> {
}
