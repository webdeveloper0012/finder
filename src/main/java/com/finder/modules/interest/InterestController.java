package com.finder.modules.interest;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/interests")
public class InterestController extends GenericRestController<Interest> {
    @Autowired
    InterestService interestService;

    public InterestController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @Override
    public GenericService<Interest> getService() {
        return interestService;
    }

    @RequestMapping("/search_interests")
    @ResponseStatus(HttpStatus.OK)
    public Resources<Interest> getInterests(@RequestParam(required = true) String interest, @RequestParam(required = false) Integer offset) {
        return new Resources<Interest>(interestService.getInterests(interest, offset));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/multiple_interests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public void postMultipleInterests(@RequestBody Map<String, String> requestBody) {
        String interests = requestBody.get("interest");
        String category = requestBody.get("category");
        interestService.postMultipleInterests(interests, category);
    }
}
