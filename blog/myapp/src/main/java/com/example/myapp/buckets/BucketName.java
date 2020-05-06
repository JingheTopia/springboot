package com.example.myapp.buckets;

public enum BucketName {
  PROFILE_IMAGE("jinghe-image");
  private final String bucketName;
  BucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public String getBucketName() {
    return bucketName;
  }
}
