package com.finder.modules.user;

import com.finder.exceptions.EntityNotFoundException;
import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.GenericService;
import com.finder.modules.role.RoleRepository;
import com.finder.modules.user.authentication.UserAuth;
import com.finder.modules.user.authentication.UserAuthService;
import com.finder.modules.user.profile.UserProfile;
import com.finder.modules.user.profile.UserProfileService;
import com.finder.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService extends GenericService<User> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserAuthService userAuthService;

    public UserService(UserRepository userRepository) {
        super(userRepository, "User");
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User", username);
        }
        return user;
    }

    @Override
    public User save(User user) {
        // after validation create the respective objects
        UserAuth userAuth = new UserAuth();
        userAuthService.createUser(user.getPhoneNumber());
        user.setId(userAuth.getId());

        return user;
    }
    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder)
            throws ErrorHolder {
        User user = (User) newEntity;
        if (user.getPassword().length() < 6) {
            errorHolder.addError("password", "Password cannot be less than 6 characters", true);
        }
        if (isNewObject(user)) {
            if (user.getUsername().length() < 6)
                errorHolder.addError("username", "Username cannot be less than 6 characters", true);
            if (userRepository.findByUsername(user.getUsername()) != null)
                errorHolder.addError("username", user.getUsername() + " already exists!", true);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            // preventing the user A from updating user B's data
            // for requests other than post, the user has authentication to the db,
            // so changing his user details
            // rather than the user id given in the entity he has passed
            User currentUser = getCurrentUser();
            user.setUsername(currentUser.getUsername());
            user.setId(currentUser.getId());
        }
    }

    @Override
    public List<User> findAll() {
        if (CommonUtil.isAdminUser()) {
            return super.findAll();
        }
        return Arrays.asList(getCurrentUser());
    }

    public User getCurrentUser() {
        String userId = CommonUtil.getCurrentUserId();
        UserAuth currentUserAuth = userAuthService.findById(userId);
        User user = new User();
        user.setUsername(currentUserAuth.getUsername());
        user.setPhoneNumber(currentUserAuth.getPhoneNumber());
        user.setUserId(currentUserAuth.getId());
        UserProfile userProfile = userProfileService.findByCurrentUser();
        if (userProfile != null) {
            user.setAboutMe(userProfile.getAboutMe());
            user.setGender(userProfile.getGender());
            user.setDateOfBirth(userProfile.getDob());
            user.setDisplayName(userProfile.getDisplayName());
            user.setLatitude(userProfile.getLatitude());
            user.setLongitude(userProfile.getLongitude());
            user.setPictureIdList(userProfile.getPictureIdList());
            user.setInterests(userProfile.getInterests());
            user.setLastLogin(currentUserAuth.getLastLogin());
        } else {
            ErrorHolder.throwError("Profile", "Profile not set yet");
        }
        return user;
    }

    @Override
    public User findById(String id) {
        if (CommonUtil.isAdminUser()) {
            return super.findById(id);
        }
        return getCurrentUser();
    }

    @Override
    public List<User> findAll(Integer offset) {
        if (CommonUtil.isAdminUser()) {
            return super.findAll(offset);
        }
        return Arrays.asList(getCurrentUser());
    }
}
