package com.finder.modules.user.profile;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.user.UserSpecificService;
import com.finder.modules.interest.Interest;
import com.finder.modules.interest.InterestService;
import com.finder.util.CommonUtil;
import com.finder.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService extends UserSpecificService<UserProfile> {
    @Autowired
    UserProfileRepo userProfileRepo;
    @Autowired
    private InterestService interestService;

    public UserProfileService(MongoRepository userProfileRepo) {
        super(userProfileRepo, "UserProfile");
    }

    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        UserProfile userProfile = (UserProfile) newEntity;
        if (!StringUtils.isEmpty(userProfile.getInterests())) {
            validateInterests(userProfile.getInterests(), errorHolder);
            userProfile.setInterests(userProfile.getInterests().toLowerCase());
        }
        super.postValidate(newEntity, errorHolder);
    }

    @Override
    public UserProfile findByCurrentUser() {
        UserProfile userProfile = userProfileRepo.findByUserId(CommonUtil.getCurrentUserId());
        if (userProfile != null) {
            return userProfile;
        }
        return null;
    }

    public UserProfile findByUserId(String userId) {
        UserProfile userProfile = userProfileRepo.findByUserId(userId);
        if (userProfile != null) {
            return userProfile;
        }
        return null;
    }

    public void addImage(String imageId) {
        UserProfile userProfile = findByCurrentUser();
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        List<String> pictureIdList = userProfile.getPictureIdList();
        if (pictureIdList == null) {
            pictureIdList = new ArrayList<>();
        }
        pictureIdList.add(imageId);
        userProfile.setPictureIdList(pictureIdList);
        super.save(userProfile);
    }

    public void validateInterests(String interests, ErrorHolder errorHolder) {
        String[] interestsList = interests.split(",");
        for (String interest : interestsList) {
            Interest interestEntity = interestService.findByInterest(interest);
            if (interestEntity == null) {
                errorHolder.addError("interest", interest + " not found");
            }
        }
        if (errorHolder.hasErrors()) {
            throw errorHolder;
        }
    }

    public void updateInterests(String interests) {
        if (StringUtils.isEmpty(interests)) {
            ErrorHolder.throwError("interest", "interest is empty");
        }
        validateInterests(interests, new ErrorHolder());
        UserProfile userProfile = findByCurrentUser();
        userProfile.setInterests(interests);
        save(userProfile);
    }

    public void updateLocation(Double latitude, Double longitude) {
        UserProfile userProfile = findByCurrentUser();
        userProfile.setLatitude(latitude);
        userProfile.setLongitude(longitude);
        userProfileRepo.save(userProfile);
    }

    public List<UserProfile> getMatchingUsers(String mood) {
        if (mood != null) {
            mood = ".*" + mood.toLowerCase() + ".*";
        }
        UserProfile currentUser = findByCurrentUser();
        Double userLatitude = currentUser.getLatitude();
        Double userLongitude = currentUser.getLongitude();
        String[] interestArray = currentUser.getInterests().split(",");

        // round 1
        Double squareDistance = Constants.SQUARE_DISTANCE;
        List<String> userSelectedList = new ArrayList<>();
        userSelectedList.add(currentUser.getUserId());
        List<UserProfile> userProfileList = new ArrayList<>();
        while (userProfileList.size() < 10) {
            Double x1 = userLatitude - squareDistance;
            Double x2 = userLatitude + squareDistance;
            Double y1 = userLongitude - squareDistance;
            Double y2 = userLongitude + squareDistance;
            if (y1 >= -180 && y1 <= 180
                    && y2 >= -180 && y2 <= 180
                    && x1 >= -90 && x1 <= 90
                    && x2 >= -90 && x2 <= 90) {
                if (mood == null) {
                    List<UserProfile> usersUnderLocation = userProfileRepo.findByCircle(x1, x2, y1, y2, userSelectedList);
                    for (UserProfile user : usersUnderLocation) {
                        for (String interest : interestArray) {
                            if (user.getInterests().contains(interest)) {
                                userProfileList.add(user);
                                userSelectedList.add(user.getUserId());
                                break;
                            }
                        }
                    }
                } else {
                    List<UserProfile> usersUnderLocation = userProfileRepo.findByMood(x1, x2, y1, y2, mood, userSelectedList);
                    for (UserProfile user : usersUnderLocation) {
                        userProfileList.add(user);
                        userSelectedList.add(user.getUserId());
                    }
                }
            } else {
                break;
            }
            // doubling the distance
            squareDistance *= 2;
        }
        return userProfileList;
    }
}
