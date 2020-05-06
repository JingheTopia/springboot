package com.example.myapp.dataStoreRepository;

import com.example.myapp.profile.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeDataStore {
  private static List<UserProfile> userProfiles = new ArrayList<>();
  static {
    userProfiles.add(new UserProfile(UUID.fromString("b6d96abb-7ccc-486f-8a17-6fb32f7d826d"), "Jean", null));
    userProfiles.add(new UserProfile(UUID.fromString("d999ba62-5c94-4d39-8365-ad70e8193a1f"), "Gan", null));
  }

  public List<UserProfile> getUserProfiles() {
    return userProfiles;
  }
}
