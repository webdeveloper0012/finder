package com.finder.modules.user.image;

import com.finder.modules.generics.user.UserSpecificService;
import com.finder.modules.user.profile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends UserSpecificService<Image> {

    @Autowired
    ImageRepo imageRepo;
    @Autowired
    UserProfileService userProfileService;

    public ImageService(ImageRepo genericRepository) {
        super(genericRepository, "Image");
    }

    public Image findById(String id) {
        return imageRepo.findById(id).orElse(null);
    }

    public String uploadImage(String contentType, byte[] imageData) {
        Image image = new Image();
        image.setMediaType(contentType);
        image.setImageData(imageData);
        super.save(image);
        userProfileService.addImage(image.getId());
        return image.getId();
    }

    @Override
    public Image findByCurrentUser() {
        return null;
    }

}
