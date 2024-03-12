package com.example.demo.service;
import com.example.demo.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void uploadImage(MultipartFile file) throws IOException {
        System.out.println("Uploading image");
        Image image = new Image();
        image.setData(file.getBytes());
        image.setFilename(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        mongoTemplate.save(image);
    }

    public List<Image> getAllImages() {
        System.out.println("Getting all images");
        return mongoTemplate.findAll(Image.class);
    }

    public Image getImageById(String id) {
        System.out.println("Getting image by id");
        return mongoTemplate.findById(id, Image.class);
    }
}
