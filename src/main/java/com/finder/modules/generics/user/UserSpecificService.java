package com.finder.modules.generics.user;

import com.finder.modules.generics.GenericService;
import com.finder.util.CommonUtil;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class UserSpecificService<T extends UserSpecificEntity> extends GenericService<T> {
    // TODO: save uses findByCurrentUser() which will be applicable only for one to one mapping
    public UserSpecificService(MongoRepository genericRepository, String entityName) {
        super(genericRepository, entityName);
    }

    @Override
    public T findById(String id) {
        T userSpecificEntity = super.findById(id);
        if (userSpecificEntity.getUserId().equals(CommonUtil.getCurrentUserId())) {
            return userSpecificEntity;
        }
        return null;
    }

    @Override
    public T save(String id, T newEntity) {
        newEntity.setUserId(CommonUtil.getCurrentUserId());
        T existingEntity = findByCurrentUser();
        if (existingEntity != null) {
            CommonUtil.copyNonNullProperties(newEntity, existingEntity);
            newEntity = existingEntity;
        }
        return super.save(id, newEntity);
    }

    @Override
    public T saveAndRefresh(T newEntity) {
        newEntity.setUserId(CommonUtil.getCurrentUserId());
        T existingEntity = findByCurrentUser();
        if (existingEntity != null) {
            CommonUtil.copyNonNullProperties(newEntity, existingEntity);
            return super.saveAndRefresh(existingEntity);
        }
        return super.saveAndRefresh(newEntity);
    }

    public abstract T findByCurrentUser();
}
