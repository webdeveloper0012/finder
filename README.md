Finder is used to find the persons with like-minded interest at a given 
point of time based on the location of the user and the interests of the users 
around them there by connecting people.

I have created a generic controller, service and entity so whenever we are trying
to create a new entity and all the http methods are to be accessed for this 
resource the same can be acheived by extending the generic controller service 
and entity.

The UserSpecificEntity with contain an user id as well for which the entity
belongs.

The following features are being handled for all the http methods which extends 
the generic classes for creating their services:

**validation**:
    developer can override the postValidate and preValidate methods of 
        service class

**populating transient attributes**:
    override populateTransientAttributes() for populating your entity specific 
        values

**UserId for entity extending UserSpecificEntity**:
    user id - taken from current user (Finder_Auth header)
    normal id is given for all entity for mongo db
    exact entity found by user id in case of post,patch and delete requests

**Avoid certain HTTP Methods of your entities**:
    developer can also avoid certain HTTP methods by providing a list of supported 
        methods to the generic rest controller super class
