package com.finder.modules.successRate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessRateRepository extends MongoRepository<SuccessRate, String> {
}
