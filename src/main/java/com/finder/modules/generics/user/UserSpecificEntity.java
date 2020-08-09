package com.finder.modules.generics.user;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;

@Data
public class UserSpecificEntity extends GenericEntity {
    private String userId;
}
