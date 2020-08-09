package com.finder.modules.generics;

import com.finder.exceptions.EntityNotFoundException;
import com.finder.exceptions.ErrorHolder;
import com.finder.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class GenericService<T extends GenericEntity> {
    @Autowired
    protected HttpServletRequest httpRequest;
    protected MongoRepository genericRepository;
    @Autowired
    SmartValidator validator;
    private String entityName;

    public GenericService(MongoRepository genericRepository, String entityName) {
        this.genericRepository = genericRepository;
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<T> findAll() {
        return findAll(null);
    }

    public T findById(String id) {
        try {
            return (T) genericRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(entityName, id));
        } catch (Throwable e) {
            return null;
        }
    }

    public T save(T entity) {
        // TODO: entity id has been updated, please check for all usecases
        // previously it was null, now id is being given
        // what if the user changes by passing data for a different user??
        return save(entity.getId(), entity);
    }

    public T save(String id, T newEntity) {
        if (id != null) {
            newEntity.setId(id);
        }
        validate(newEntity);
        return (T) genericRepository.save(newEntity);
    }

    public T saveAndRefresh(T newEntity) {
        validate(newEntity);
        genericRepository.save(newEntity);
        newEntity.populateTransientValues();
        return newEntity;
    }

    public void delete(T entity) {
        genericRepository.delete(entity);
    }

    protected <T extends GenericEntity> void validate(T newEntity) {
        ErrorHolder errorHolder = new ErrorHolder();
        preValidate(newEntity, errorHolder);
        BindException errors = new BindException(newEntity, newEntity.toString());
        validator.validate(newEntity, errors);
        if (errors.hasErrors()) {
            for (ObjectError objectError : errors.getAllErrors()) {
                FieldError fieldError = (FieldError) objectError;
                errorHolder.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        if (!errorHolder.hasErrors())
            postValidate(newEntity, errorHolder);
        if (errorHolder.hasErrors())
            throw errorHolder;
    }

    /***
     * method to set properties pre-validation
     * @param <T>
     * @param newEntity
     */

    public <T extends GenericEntity> void preValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {

    }

    /***
     * method to set properties post-validation
     * @param <T>
     * @param newEntity
     */

    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {

    }

    protected boolean isNewObject(T entity) {
        // TODO: have a mechanism to know whether its new entity or not
        return HttpMethod.POST.name().equals(httpRequest.getMethod()) && entity.getId() == null;
    }

    public List<T> findAll(Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        return genericRepository.findAll(PageRequest.of(offset, Constants.PAGINATION_SIZE)).getContent();
    }
}
