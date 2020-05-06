package com.example.myapp.profile;

import static org.apache.http.entity.ContentType.*;

import com.example.myapp.buckets.BucketName;
import com.example.myapp.fileStoreService.FileStore;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {
  private final UserProfileDAO userProfileDAO;
  private final FileStore fileStore;

  @Autowired
  public UserProfileService(UserProfileDAO userProfileDAO, FileStore fileStore) {
    this.userProfileDAO = userProfileDAO;
    this.fileStore = fileStore;
  }

  public List<UserProfile> getUserProfiles(){
    return userProfileDAO.getUserProfiles();
  }

  public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    //1. If an image is not empty
    isFileEmpty(file);

    //2. If a file is an image
    isImage(file);

    //3. If the user is in the database.
    UserProfile user = getUser(userProfileId);

    //4. Grab the metadata from the file
    Map<String, String> metadata = ExtractMetadata(file);

    //5. Store the image in S3 and update the database with S3 link.
    String path = String.format("%s/%s",  BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
    try {
      fileStore.save(path,file.getOriginalFilename(), Optional.of(metadata),file.getInputStream());
      user.setProfileImageLink(file.getOriginalFilename());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  public byte[] downloadUserProfileImage(UUID userProfileId) {
    UserProfile user = getUser(userProfileId);
    String path = String.format("%s/%s",
        BucketName.PROFILE_IMAGE.getBucketName(),
        user.getId());
    return user.getProfileImageLink()
         .map(key -> fileStore.download(path, key))
         .orElse(new byte[0]);
  }

  private Map<String, String> ExtractMetadata(MultipartFile file) {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    return metadata;
  }

  private void isImage(MultipartFile file) {
    if (!Arrays.asList(IMAGE_JPEG.getMimeType(),
        IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType()))
      throw new IllegalStateException("File must be an image");
  }

  private void isFileEmpty(MultipartFile file) {
    if(file.isEmpty())
      throw new IllegalStateException("Cannot upload empty files");
  }

  private UserProfile getUser(UUID userProfileId){
    return userProfileDAO.getUserProfiles()
        .stream()
        .filter(userProfile -> userProfile.getId().equals(userProfileId))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(String.format("Cannot find %s user", userProfileId)));
  }


}


