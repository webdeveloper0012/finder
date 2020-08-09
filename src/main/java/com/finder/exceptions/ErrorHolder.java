package com.finder.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorHolder extends RuntimeException {
    /**
     * Holder for errors to be shown to the com.finder.modules.user
     * TODO: Warning messages can be provided with a warning list same as error list
     */
    private List<Error> errors;

    public ErrorHolder() {
    }

    public ErrorHolder(String component, String details) {
        addError(component, details);
    }

    public void addError(String component, String details, boolean throwError) {
        addError(component, details);
        if (throwError)
            throw this;
    }

    public void addError(String component, String details) {
        if (errors == null) {
            this.errors = new ArrayList<>();
        }
        errors.add(new Error(component, details));
    }

    public boolean hasErrors() {
        return errors != null && errors.size() != 0;
    }

    public List<Error> getResponse() {
        return errors;
    }

    public static void throwError(String component, String details) {
        new ErrorHolder().addError(component, details, true);
    }
}

@Data
class Error {
    private String component;
    private String details;

    public Error(String component, String details) {
        this.component = component;
        this.details = details;
    }
}