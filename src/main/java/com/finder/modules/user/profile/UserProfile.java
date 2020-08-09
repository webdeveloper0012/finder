package com.finder.modules.user.profile;

import com.finder.modules.generics.user.UserSpecificEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "user_profile")
public class UserProfile extends UserSpecificEntity {
    private String displayName;
    private Character gender;
    // TODO: 1994-07-15T00:00:00.000+0000 - change date format in output
    private Date dob;
    private String aboutMe;
    private String deviceId;
    private Double latitude;
    private Double longitude;
    private List<String> pictureIdList;
    // populate the id from interest table
    //private List<String> interestList;

    // comma separated strings
    private String interests;
}
