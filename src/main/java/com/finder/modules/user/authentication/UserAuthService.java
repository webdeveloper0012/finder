package com.finder.modules.user.authentication;

import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService extends GenericService<UserAuth> {
    @Autowired
    private UserAuthRepo userAuthRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAuthService(UserAuthRepo userAuthRepo) {
        super(userAuthRepo, "User");
    }

    @Override
    public UserAuth save(UserAuth userAuth) {
        userAuth.setLastLogin(System.currentTimeMillis());
        return super.save(userAuth);
    }

    public UserAuth saveWithoutLoggingtime(UserAuth userAuth) {
        return super.save(userAuth);
    }

    public UserAuth createUser(String phoneNumber) {
        // if user is already authenticated, then reset password usecase
        UserAuth userAuth = userAuthRepo.findByPhoneNumber(phoneNumber);
        if (userAuth == null) {
            userAuth = new UserAuth();
            userAuth.setUsername(phoneNumber);
            userAuth.setPhoneNumber(phoneNumber);
        }
        String authToken = bCryptPasswordEncoder.encode(System.currentTimeMillis() + phoneNumber);
        userAuth.setAuthToken(authToken);
        userAuth.setIsValid(Boolean.TRUE);
        return save(userAuth);
    }

    public UserAuth findByAuthToken(String authToken) {
        return userAuthRepo.findByAuthToken(authToken);
    }
}
