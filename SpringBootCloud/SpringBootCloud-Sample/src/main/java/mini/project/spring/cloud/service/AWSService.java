package mini.project.spring.cloud.service;

import java.io.IOException;
import java.util.List;

public interface AWSService {
    
    public void uploadFileToS3();
    
    public String downloadFileFromS3() throws IOException;

    public List<String> listFilesInBucket() throws IOException;

    public void deleteFileInBucket();
}
