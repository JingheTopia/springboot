package com.example.myapp.profile;

import com.example.myapp.dataStoreRepository.FakeDataStore;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDAO {
  private final FakeDataStore fakeDataStore;

  @Autowired
  public UserProfileDAO(FakeDataStore fakeDataStore) {
    this.fakeDataStore = fakeDataStore;
  }

  public List<UserProfile> getUserProfiles(){
    return this.fakeDataStore.getUserProfiles();
  }

}
