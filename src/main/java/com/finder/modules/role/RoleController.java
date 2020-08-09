package com.finder.modules.role;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/roles")
public class RoleController extends GenericRestController<Role> {
    @Autowired
    RoleService roleService;

    public RoleController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.PATCH, HttpMethod.DELETE));
    }

    @Override
    public GenericService<Role> getService() {
        return roleService;
    }
}
