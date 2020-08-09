package com.finder.modules.generics;

import com.finder.exceptions.EntityNotFoundException;
import com.finder.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class GenericRestController<T extends GenericEntity> {
    protected List<HttpMethod> supportedMethods;

    public GenericRestController(List<HttpMethod> supportedMethods) {
        this.supportedMethods = supportedMethods;
    }

    private void isMethodSupported(HttpMethod httpMethod) throws Throwable {
        if (!supportedMethods.contains(httpMethod)) {
            List<String> supportedMethodsInString = new ArrayList<>();
            supportedMethods.forEach(httpMethod1 -> supportedMethodsInString.add(httpMethod1.toString()));
            throw new HttpRequestMethodNotSupportedException(httpMethod.toString(), supportedMethodsInString);
        }
    }

    @Transactional
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    T postEntity(@RequestBody T entity) throws Throwable {
        isMethodSupported(HttpMethod.POST);
        return getService().save(entity);
    }

    @Transactional
    @RequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    T putEntity(@RequestBody T newEntity, @PathVariable String id) throws Throwable {
        isMethodSupported(HttpMethod.PUT);
        T existingEntity = getService().findById(id);
        return getService().save(id, newEntity);
    }

    @Transactional
    @RequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    T patchEntity(@RequestBody T newEntity, @PathVariable String id) throws Throwable {
        isMethodSupported(HttpMethod.PATCH);
        T existingEntity = getService().findById(id);
        CommonUtil.copyNonNullProperties(newEntity, existingEntity);
        return getService().save(id, existingEntity);
    }

    @Transactional
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEntity(@PathVariable String id) throws Throwable {
        isMethodSupported(HttpMethod.DELETE);
        T entity = getService().findById(id);
        getService().delete(entity);
    }

    @Transactional(readOnly = true)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    Resources<T> getEntities(@RequestParam(required = false) String id, @RequestParam(required = false) Integer offset) throws Throwable {
        isMethodSupported(HttpMethod.GET);
        if (id != null) {
            T entity = getService().findById(id);
            if (entity == null) {
                throw new EntityNotFoundException(getService().getEntityName(), id);
            }
            return new Resources<T>(Arrays.asList(entity));
        } else {
            List entityList = getService().findAll(offset).stream()
                    .map(entity -> new Resource<T>(entity))
                    .collect(Collectors.toList());
            return new Resources<T>(entityList);
        }
    }

    public abstract GenericService<T> getService();
}
