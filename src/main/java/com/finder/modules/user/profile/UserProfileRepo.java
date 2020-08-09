package com.finder.modules.user.profile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepo extends MongoRepository<UserProfile, String> {
    UserProfile findByUserId(String userId);

    @Query("{'latitude' : { $gte: ?0, $lte: ?1 }, 'longitude' : { $gte: ?2, $lte: ?3}, 'userId' : { $nin : ?4} }")
    List<UserProfile> findByCircle(Double x1, Double x2, Double y1, Double y2, List alreadySelectedUsers);

    @Query("{'latitude' : { $gte: ?0, $lte: ?1 }, 'longitude' : { $gte: ?2, $lte: ?3}, 'interests' :  {$regex : ?4} , 'userId' : { $nin : ?5} }")
    List<UserProfile> findByMood(Double x1, Double x2, Double y1, Double y2, String interest, List alreadySelectedUsers);
}
