package com.finder.modules.notification;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    @Query("{'id' : { $in : ?0 }}")
    List<Notification> findAllById(List<String> idList, Sort sort);
}
