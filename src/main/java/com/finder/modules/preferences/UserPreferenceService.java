package com.finder.modules.preferences;

import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService extends GenericService<UserPreference> {
    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    public UserPreferenceService(UserPreferenceRepository genericRepository) {
        super(genericRepository, "UserPreference");
    }
}
