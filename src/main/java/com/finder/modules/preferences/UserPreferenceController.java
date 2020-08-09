package com.finder.modules.preferences;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/user_preferences")
public class UserPreferenceController extends GenericRestController<UserPreference> {
    @Autowired
    UserPreferenceService userPreferenceService;

    public UserPreferenceController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<UserPreference> getService() {
        return userPreferenceService;
    }
}
