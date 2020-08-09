package com.finder.modules.MoodMessage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodMessageRepository extends MongoRepository<MoodMessage, String> {
}
