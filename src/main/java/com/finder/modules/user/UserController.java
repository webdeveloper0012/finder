package com.finder.modules.user;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import com.finder.modules.user.authentication.UserAuth;
import com.finder.modules.user.profile.UserProfile;
import com.finder.modules.user.profile.UserProfileService;
import com.finder.modules.user.registration.UserRegistration;
import com.finder.modules.user.registration.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends GenericRestController<User> {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserProfileService userProfileService;

    public UserController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<User> getService() {
        return userService;
    }

    @Transactional
    @RequestMapping(value = "/user_profile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody UserProfile userProfile) {
        userProfileService.save(userProfile);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/user_exists", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String isUserExists(@RequestParam("user_name") String username) {
        JSONObject responseJson = new JSONObject();
        boolean result = false;
        if (username != null && userService.findByUsername(username) != null) {
            result = true;
        }
        try {
            responseJson.put("exists", result);
        } catch (JSONException e) {
            log.error("Exception occurred while checking com.finder.modules.user existence ::: ", e);
        }
        return responseJson.toString();
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register_phone_number", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void register_phone_number(@RequestBody UserRegistration userRegistration) {
        userRegistrationService.save(userRegistration);
    }

    @Transactional
    @RequestMapping(value = "/verify_otp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String verify_otp(@RequestBody UserRegistration userRegistration, HttpServletResponse response) {
        UserAuth userAuth = userRegistrationService.verifyOtp(userRegistration);
        String authToken = null;
        if (userAuth != null) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            authToken = userAuth.getAuthToken();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return authToken;
    }

    @RequestMapping(value = "/update_location", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void update_location(@RequestParam(name = "latitude") Double latitude,
                                @RequestParam(name = "longitude") Double longitude) {
        userProfileService.updateLocation(latitude, longitude);
    }

    @RequestMapping(value = "/update_interests", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void update_interests(@RequestParam(name = "interests") String interests) {
        userProfileService.updateInterests(interests);
    }

    @RequestMapping(value = "/search_mood", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserProfile> searchMood(@RequestParam(name = "mood", required = false) String interest) {
        return userProfileService.getMatchingUsers(interest);
    }

    @RequestMapping(value = "/matching_users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserProfile> matchingUsers() {
        return userProfileService.getMatchingUsers(null);
    }
}
