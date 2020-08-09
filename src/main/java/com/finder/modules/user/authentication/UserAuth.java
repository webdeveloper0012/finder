package com.finder.modules.user.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "user_auth")
public class UserAuth extends GenericEntity {
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Phone Number cannot be null")
    @NotEmpty(message = "Phone Number cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;

    // encoded with bcryptencoder
    private String authToken;

    // time in milliseconds
    private Long lastLogin;
    // can be invalidated if new otp is to be generated
    // or some deactivate account stuff
    private Boolean isValid;
}
