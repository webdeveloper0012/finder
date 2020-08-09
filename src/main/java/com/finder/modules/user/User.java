package com.finder.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finder.modules.generics.GenericEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
// transient entity as no apparent values are being set in this collection
public class User extends GenericEntity {
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull(message = "Phone number cannot be null")
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;
    @JsonIgnore
    private Integer phoneNumberOtp;
    private Character gender;
    private Date dateOfBirth;
    private String email;
    private String displayName;
    // user id is there in user picture model
    //private List<String> picList;
    private List<String> chatList;
    private List<String> interestList;
    private List<String> blockedUserList;
    private List<String> pictureIdList;
    private String aboutMe;
    private String work;
    private String education;
    private Long numberOfLikes;
    private Long personalMessageCount;
    private String socialLink;
    private Long lastLogin;
    private Long creationDate;
    private Double latitude;
    private Double longitude;
    @JsonIgnore
    private Integer emailOtp;
    // from UserAuth
    private String userId;
    // comma separated strings
    private String interests;

    public User() {
    }
}
