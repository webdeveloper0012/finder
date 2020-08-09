package com.finder.modules.user.notification;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends MongoRepository<UserNotification, String> {
    List<UserNotification> findByUserId(String userId, PageRequest pageable);
}
