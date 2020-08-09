package com.finder.modules.generics;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public abstract class GenericEntity {
    @Id
    private String id;
    public GenericEntity() {
    }

    public void populateTransientValues() {
    }

    ;
}
