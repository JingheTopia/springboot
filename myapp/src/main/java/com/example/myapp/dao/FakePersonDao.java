package com.example.myapp.dao;
import com.example.myapp.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository("fakeDao")
public class FakePersonDao implements PersonDao {
  private static List<Person> DB = new ArrayList<>();
  @Override
  public int insertPerson(UUID id, Person person) {
    DB.add( new Person(id, person.getName()));
    return 1;
  }

  @Override
  public List<Person> selectAllPeople() {
    return DB;
  }

  @Override
  public int deletePersonById(UUID id) {
    Optional personMaybe = selectPersonById(id);
    if(personMaybe.isEmpty())
      return 0;
    DB.remove(personMaybe.get());
    return 1;
  }

  @Override
  public int updatePersonById(UUID id, Person person) {
    return selectPersonById(id).map(person1 -> {
      int indexToUpdate = (DB.indexOf(person1));
      if (indexToUpdate >= 0 ){
        DB.set(indexToUpdate, person);
        return 1;
      }
        return 0;
    }).orElse(0);
  }

  @Override
  public Optional<Person> selectPersonById(UUID id) {
    return DB.stream().filter(person -> person.
        getId().equals(id)).findFirst();
  }

}
