package com.example.myapp.fileStoreService;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStore {

  private final AmazonS3 S3;

  @Autowired
  public FileStore(AmazonS3 S3) {
    this.S3 = S3;
  }

  public void save(String path,
                   String fileName,
                   Optional<Map<String, String>> optionalMetadata,
                   InputStream inputStream){
    ObjectMetadata objectMetadata = new ObjectMetadata();
    optionalMetadata.ifPresent(map ->{
      if(!map.isEmpty()){
        map.forEach(objectMetadata::addUserMetadata);
      }
    });

    try{
      S3.putObject(path,fileName,inputStream,objectMetadata);
    } catch (AmazonServiceException e){
      throw new IllegalStateException("Failed to store file to AWS", e);
    }
  }

  public byte[] download(String path, String key) {
    try{
      return IOUtils.toByteArray(S3.getObject(path, key).getObjectContent());
    } catch (AmazonServiceException | IOException e){
      throw new IllegalStateException("Failed to store file to AWS", e);
    }
  }
}
