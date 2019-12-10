package mini.project.spring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mini.project.spring.cloud.service.AWSService;

@RestController
@RequestMapping(path = "aws")
public class AWSManager {
    
    @Autowired
    private AWSService service;

    @PostMapping(path = "s3")
    public void uploadFileToS3() {
        service.uploadFileToS3();
    }

    @GetMapping(path = "s3")
    public String downloadFileFromS3() {
        try {
            return service.downloadFileFromS3();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
