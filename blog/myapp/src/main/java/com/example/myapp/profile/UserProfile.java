package com.example.myapp.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
  private final UUID id;
  private final String name;
  private String profileImageLink;

  public UserProfile(UUID id, String name, String profileImageLink) {
    this.id = id;
    this.name = name;
    this.profileImageLink = profileImageLink;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Optional<String> getProfileImageLink() {
    return Optional.ofNullable(profileImageLink);
  }

  public void setProfileImageLink(String profileImageLink) {
    this.profileImageLink = profileImageLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserProfile)) {
      return false;
    }
    UserProfile that = (UserProfile) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(profileImageLink, that.profileImageLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, profileImageLink);
  }
}
