package com.example.demo.controller;
import com.example.demo.models.Image;
import com.example.demo.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.uploadImage(file);
            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/all")
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    public void getImageById(@PathVariable String id, HttpServletResponse response) throws IOException {
        Image image = imageService.getImageById(id);
        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getData());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
