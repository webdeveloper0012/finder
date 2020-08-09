package com.finder.modules.role;

import com.finder.modules.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);

    @Query("{ 'roles.users' : ?0}")
    List<Role> findByUser(User user);

    @Query("{ 'roleId' : ?0, 'roles.users' : ?1}")
    Role findById(String roleId, User user);
}
