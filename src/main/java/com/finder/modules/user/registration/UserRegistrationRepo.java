package com.finder.modules.user.registration;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepo extends MongoRepository<UserRegistration, String> {
    UserRegistration findByPhoneNumber(String phoneNumber);
}
