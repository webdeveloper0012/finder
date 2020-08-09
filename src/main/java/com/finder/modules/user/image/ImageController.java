package com.finder.modules.user.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        Image image = imageService.findById(id);
        if (image != null) {
            MediaType mediaType = MediaType.parseMediaType(image.getMediaType());
            return ResponseEntity.ok().contentType(mediaType).body(
                    image.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        if (image.getSize() > 16777216) {
            // greater than 16mb
            return ResponseEntity.badRequest().body("Image too big");
        }
        String contentType = image.getContentType();
        byte[] imageData = image.getBytes();
        String imageId = imageService.uploadImage(contentType, imageData);
        return ResponseEntity.ok(imageId);
    }

}
