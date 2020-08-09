package com.finder.modules.category;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends GenericService<Category> {
    @Autowired
    CategoryRespository categoryRespository;

    public CategoryService(CategoryRespository genericRepository) {
        super(genericRepository, "Category");
    }

    @Override
    public <T extends GenericEntity> void preValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        Category category = (Category) newEntity;
        if (categoryRespository.findByCategory(category.getCategory()) != null) {
            errorHolder.addError("Category", "Category already exists!");
        }
        super.preValidate(newEntity, errorHolder);
    }

    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        Category category = (Category) newEntity;
        if (isNewObject(category)) {
            if (category.getNumberOfUsers() == null) {
                category.setNumberOfUsers(0L);
            }
        }
        super.postValidate(newEntity, errorHolder);
    }

    public Category findByCategory(String category) {
        return categoryRespository.findByCategory(category);
    }
}
