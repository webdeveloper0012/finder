package com.finder.modules.reportedUsers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedUsersRepository extends MongoRepository<ReportedUsers, String> {
}
