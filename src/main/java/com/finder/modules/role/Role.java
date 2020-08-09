package com.finder.modules.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.user.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Document(collection = "roles")
@Relation(collectionRelation = "roles")
public class Role extends GenericEntity {
    @NotEmpty
    private String name;
    @JsonIgnore
    private Set<User> users;

    public Role() {
    }

    public Role(@NotEmpty String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }
}
