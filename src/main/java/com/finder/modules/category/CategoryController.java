package com.finder.modules.category;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/categories")
public class CategoryController extends GenericRestController<Category> {

    @Autowired
    CategoryService categoryService;

    public CategoryController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @Override
    public GenericService<Category> getService() {
        return categoryService;
    }
}
