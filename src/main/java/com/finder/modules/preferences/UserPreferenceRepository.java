package com.finder.modules.preferences;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends MongoRepository<UserPreference, String> {
}
