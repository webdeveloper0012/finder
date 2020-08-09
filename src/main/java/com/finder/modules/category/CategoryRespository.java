package com.finder.modules.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends MongoRepository<Category, String> {
  Category findByCategory(String category);
}
