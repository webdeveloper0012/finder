package com.finder.exceptions;

public class EntityNotFoundException extends ErrorHolder {

    /**
     * Entity not found for the given value
     */
    private static final long serialVersionUID = 3519670375969938521L;

    public EntityNotFoundException(String entityName, Object entityValue) {
        super(entityName, entityName + ": " + entityValue + " not found");
    }
 
}
