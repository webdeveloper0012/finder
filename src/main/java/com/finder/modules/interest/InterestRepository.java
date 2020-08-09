package com.finder.modules.interest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends MongoRepository<Interest, String> {
    Interest findByInterest(String interest);

    List<Interest> findByInterestLike(String interest, PageRequest pageRequest);
}
