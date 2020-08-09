package com.finder.modules.category;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "categories")
public class Category extends GenericEntity {
    private Long numberOfUsers;
    @NotEmpty(message = "Category cannot be empty")
    @NotNull(message = "Category cannot be null")
    private String category;
}
