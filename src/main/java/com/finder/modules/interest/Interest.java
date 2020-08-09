package com.finder.modules.interest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "interests")
public class Interest extends GenericEntity {
    @NotNull(message = "Interest cannot be null")
    @NotEmpty(message = "Interest cannot be empty")
    // maintain all lowercase to help in search and retrieval
    private String interest;
    @JsonIgnore
    private String categoryId;
    @NotNull(message = "Category cannot be null")
    @NotEmpty(message = "Category cannot be empty")
    private String category;
    private Long numberOfUsers;


    public String getInterest() {
        if (interest != null)
            return interest.toLowerCase();
        return null;
    }
}
