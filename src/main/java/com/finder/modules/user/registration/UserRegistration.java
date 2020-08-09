package com.finder.modules.user.registration;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRegistration extends GenericEntity {
    @NotEmpty(message = "Phone number cannot be empty")
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    // keeping string just in case we change to alpha numeric
    @NotEmpty(message = "OTP cannot be empty")
    @NotNull(message = "OTP cannot be null")
    private String mobileOTP;
    private String userId;
}
