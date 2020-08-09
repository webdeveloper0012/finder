package com.finder.modules.chatWindow;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatWindowRepository extends MongoRepository<ChatWindow, String> {
}
