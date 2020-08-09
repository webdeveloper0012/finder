package com.finder.modules.preferences;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_preferences")
public class UserPreference extends GenericEntity {
    private String userId;
    private char preferredGender;
    private Integer minAge;
    private Integer maxAge;
    private Boolean hideAge;
    private Boolean hideActiveDate;
    private Boolean hideDistance;
}
