package com.finder.modules.MoodMessage;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/interest_message")
public class MoodMessageController extends GenericRestController<MoodMessage> {
    @Autowired
    MoodMessageService moodMessageService;

    public MoodMessageController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<MoodMessage> getService() {
        return moodMessageService;
    }
}
