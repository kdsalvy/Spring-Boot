package mini.project.spring.cloud.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import mini.project.spring.cloud.service.AWSService;

@Service
public class AWSServiceImpl implements AWSService {

    Logger logger = LoggerFactory.getLogger(AWSServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void uploadFileToS3() {
        // TODO Auto-generated method stub

    }

    @Override
    public String downloadFileFromS3() throws IOException {
        Resource resource = this.resourceLoader.getResource("s3://kd-spc-demo/test.txt");
        File downloadedS3Object = new File(resource.getFilename());

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, downloadedS3Object.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        byte[] bytes = Files.readAllBytes(downloadedS3Object.toPath());
        downloadedS3Object.delete();

        return new String(bytes);
    }

    @Override
    public List<String> listFilesInBucket() throws IOException {
        List<String> fileContentList = getAllFileContents();
        return fileContentList;
    }

    private List<String> getAllFileContents() throws IOException {
        ListObjectsV2Result result = amazonS3.listObjectsV2("kd-spc-bucket");
        List<S3ObjectSummary> s3ObjectSummaryList = result.getObjectSummaries();
        List<String> contentList = new ArrayList<>();

        for (S3ObjectSummary objectSummary : s3ObjectSummaryList) {
            S3Object object = amazonS3.getObject(objectSummary.getBucketName(), objectSummary.getKey());
            if (!object.getObjectMetadata()
                .getContentType()
                .equals("application/x-directory")) {
                logger.info(objectSummary.getKey());
                File tempFile = new File("temp.txt");
                try (S3ObjectInputStream inputStream = object.getObjectContent(); FileOutputStream fos = new FileOutputStream(tempFile)) {
                    byte[] read_buf = new byte[1024];
                    int read_len = 0;
                    while ((read_len = inputStream.read(read_buf)) > 0) {
                        fos.write(read_buf, 0, read_len);
                    }
                    byte[] bytes = Files.readAllBytes(tempFile.toPath());
                    contentList.add(new String(bytes));
                }
                tempFile.delete();
            }
        }
        return contentList;
    }

}
