package com.finder.modules.reportedUsers;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/reported_users")
public class ReportedUsersController extends GenericRestController<ReportedUsers> {
    @Autowired
    ReportedUsersService reportedUsersService;

    public ReportedUsersController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<ReportedUsers> getService() {
        return reportedUsersService;
    }
}
