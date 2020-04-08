package mini.project.spring.cloud.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import mini.project.spring.cloud.service.AWSService;

@Service
public class AWSServiceImpl implements AWSService {

    private static Logger logger = LoggerFactory.getLogger(AWSServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AWSSecretsManagerClient asmClient;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void uploadFileToS3() {
        File file = new File("temp.txt");
        amazonS3.putObject("kd-spc-demo", "temp.txt", file);
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
        logger.info("List Objects from bucket");
        ListObjectsV2Result result = amazonS3.listObjectsV2("kd-spc-bucket");
        List<String> contentList = new ArrayList<>();

        logger.info("Filter specific objects");
        Predicate<S3ObjectSummary> files = objectSummary -> {
            ObjectMetadata metadata = amazonS3.getObjectMetadata(objectSummary.getBucketName(), objectSummary.getKey());
            String[] keyPartsArray = objectSummary.getKey()
                .split("/");
            return !metadata.getContentType()
                .equals("application/x-directory") && keyPartsArray[keyPartsArray.length - 1].matches("test[a-zA-Z]{0,}.txt");
        };
        List<S3ObjectSummary> s3ObjectSummaryList = result.getObjectSummaries()
            .stream()
            .filter(files)
            .collect(Collectors.toList());

        for (S3ObjectSummary objectSummary : s3ObjectSummaryList) {
            logger.info("Get Object");
            S3Object object = amazonS3.getObject(objectSummary.getBucketName(), objectSummary.getKey());
            logger.info(objectSummary.getKey());
            File tempFile = new File("temp.txt");
            logger.info("Read Object");
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
        return contentList;
    }

    @Override
    public void deleteFileInBucket() {
        amazonS3.deleteObject("kd-spc-bucket", "A/E/test.txt");
    }

    @Override
    public String fetchSecretValue(String key) {
        GetSecretValueRequest request = new GetSecretValueRequest();
        request.setSecretId("/sample/secrets");

        GetSecretValueResult result = asmClient.getSecretValue(request);
        String jsonString = result.getSecretString();

        JSONParser parser = new JSONParser(jsonString);
        Map<?, ?> secretMap = null;
        try {
            Object object = parser.parse();
            if(object instanceof LinkedHashMap)
                secretMap = (LinkedHashMap<?, ?>)object ;
            return (String) secretMap.get(key);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
