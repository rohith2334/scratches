//package com.example.demo.controller;
//
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.data.mongodb.gridfs.GridFsOperations;
//import org.springframework.data.mongodb.gridfs.GridFsTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import com.example.demo.service.FileeService;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@RestController
//public class FileeController {
//
//    @Autowired
//    private FileeService  fileeService;
//
//    @Autowired
//    private GridFsTemplate gridFsTemplate;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileId = fileeService.uploadFile(file);
//            return ResponseEntity.ok(fileId);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
//        }
//    }
//
//    @GetMapping("/files/{id}")
//    public void getFileById(@PathVariable String id, HttpServletResponse response) {
//        Query query = Query.query(Criteria.where("_id").is(id));
//        GridFsOperations gridFsOperations = new GridFsTemplate(mongoTemplate.getDb());
//        InputStream inputStream = gridFsOperations.getResource(query).getInputStream();
//        try {
//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            response.setHeader("Content-Disposition", "attachment; filename=" + id); // Change to the filename if available
//            org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//    }
//}
