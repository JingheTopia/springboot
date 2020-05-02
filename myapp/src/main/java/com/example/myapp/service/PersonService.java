package com.example.myapp.service;

import com.example.myapp.dao.PersonDao;
import com.example.myapp.model.Person;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
  private final PersonDao personDao;

  @Autowired
  public PersonService(@Qualifier("fakeDao") PersonDao personDao) {
    this.personDao = personDao;
  }

  public int insertPerson(Person person){
    return personDao.insertPerson(person);
  }

  public List<Person> getAllPerson(){
    return this.personDao.selectAllPeople();
  }

  public Optional<Person> selectPersonById(UUID id){
    return this.personDao.selectPersonById(id);
  }

  public int deletePerson(UUID id){
    return this.personDao.deletePersonById(id);
  }

  public int updatePerson(UUID id, Person person){
    return this.personDao.updatePersonById(id, new Person(id, person.getName()));
  }
}
