package com.finder.modules.user.registration;

import com.finder.modules.generics.GenericService;
import com.finder.modules.user.authentication.UserAuth;
import com.finder.modules.user.authentication.UserAuthService;
import com.finder.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService extends GenericService<UserRegistration> {
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;

    @Autowired
    private UserAuthService userAuthService;

    public UserRegistrationService(UserRegistrationRepo userRegistrationRepo) {
        super(userRegistrationRepo, "User Registration");
    }

    @Override
    public UserRegistration save(UserRegistration userRegistration) {
        UserRegistration existingUser = userRegistrationRepo.findByPhoneNumber(userRegistration.getPhoneNumber());
        if (existingUser != null) {
            userRegistration = existingUser;
            try {
                UserAuth userAuth = userAuthService.findById(existingUser.getUserId());
                if (userAuth != null) {
                    userAuth.setIsValid(Boolean.FALSE);
                    userAuthService.save(userAuth);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        String mobileOTP = CommonUtil.getOtp(4);
        userRegistration.setMobileOTP(mobileOTP);
        // TODO: send the otp to the mobile device
        return super.save(userRegistration);
    }

    public UserAuth verifyOtp(UserRegistration userRegistration) {
        validate(userRegistration);
        UserRegistration existingUser = userRegistrationRepo.findByPhoneNumber(userRegistration.getPhoneNumber());
        if (existingUser == null) {
            return null;
        } else if (existingUser.getMobileOTP().equals(userRegistration.getMobileOTP())) {
            UserAuth userAuth = userAuthService.createUser(userRegistration.getPhoneNumber());
            userRegistration.setUserId(userAuth.getId());
            userRegistration.setId(existingUser.getId());
            // calling super, as no need to validate or assign new otp if user already exists
            super.save(userRegistration);
            return userAuth;
        }
        return null;
    }
}
