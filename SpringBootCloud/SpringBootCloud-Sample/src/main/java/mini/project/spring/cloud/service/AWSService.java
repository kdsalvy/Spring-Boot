package mini.project.spring.cloud.service;

import java.io.IOException;

public interface AWSService {
    
    public void uploadFileToS3();
    
    public String downloadFileFromS3() throws IOException;
}
