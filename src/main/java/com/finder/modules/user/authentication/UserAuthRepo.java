package com.finder.modules.user.authentication;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAuthRepo extends MongoRepository<UserAuth, String> {
    UserAuth findByPhoneNumber(String phoneNumber);
    UserAuth findByAuthToken(String authToken);
}
