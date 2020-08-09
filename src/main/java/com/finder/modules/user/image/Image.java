package com.finder.modules.user.image;

import com.finder.modules.generics.user.UserSpecificEntity;
import lombok.Data;

@Data
public class Image extends UserSpecificEntity {
    private byte[] imageData;
    private String mediaType;
}
