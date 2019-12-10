package mini.project.spring.cloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import mini.project.spring.cloud.service.AWSService;

@Service
public class AWSServiceImpl implements AWSService {
    
    Logger logger = LoggerFactory.getLogger(AWSServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;
    
    private String s3URL = "s3://kd-spc-bucket/test.txt";

    @Override
    public void uploadFileToS3() {
        // TODO Auto-generated method stub

    }

    @Override
    public String downloadFileFromS3() throws IOException {
        // Get all file or folders present in the bucket

        Resource resource = this.resourceLoader.getResource(s3URL);
        File downloadedS3Object = new File(resource.getFilename());
        
        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, downloadedS3Object.toPath(), 
              StandardCopyOption.REPLACE_EXISTING);
        }
        
        byte[] bytes = Files.readAllBytes(downloadedS3Object.toPath());
        
        return new String(bytes);
    }

}
