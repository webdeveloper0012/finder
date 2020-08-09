package com.finder.scope.request;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FinderParams {
    private HashMap<Integer, Object> requestParams;

    public FinderParams() {
        requestParams = new HashMap<>();
    }

    private HashMap<Integer, Object> getRequestParams() {
        return requestParams;
    }

    public Object getRequestParam(Integer key) {
        return getRequestParams().get(key);
    }

    public Boolean getBoolean(Integer key) {
        Object value = getRequestParam(key);
        if (value == null) {
            return false;
        }
        return (Boolean) value;
    }

    public Integer getInteger(Integer key) {
        return (Integer) getRequestParam(key);
    }

    public String getString(int key) {
        return (String) getRequestParam(key);
    }

    public void putRequestParam(Integer key, Object value) {
        getRequestParams().put(key, value);
    }
}
