package com.finder.modules.successRate;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/success_rate")
public class SuccessRateController extends GenericRestController<SuccessRate> {

    @Autowired
    SuccessRateService successRateService;

    public SuccessRateController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<SuccessRate> getService() {
        return successRateService;
    }
}
