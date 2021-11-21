package com.querydsl.example.dao;

import com.querydsl.example.dto.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PersonDaoTest extends AbstractDaoTest {

    @Autowired
    PersonDao personDao;

    @Test
    public void findAll() {
        List<Person> persons = personDao.findAll();
        assertFalse(persons.isEmpty());
    }

    @Test
    public void findById() {
        List<Person> persons = personDao.findAll();
        assertNotNull(personDao.findById(1));
    }

    @Test
    public void update() {
        Person person = personDao.findById(1);
        personDao.save(person);
    }

    @Test
    public void delete() {
        Person person = new Person();
        person.setEmail("john@acme.com");
        personDao.save(person);
        assertNotNull(person.getId());
        personDao.delete(person);
        assertNull(personDao.findById(person.getId()));
    }

    @Test
    //It tests the count method. Added to increase method coverage to 100%
    public void count(){
        List<Person> persons = personDao.findAll();
        assertEquals(persons.size(),personDao.count());
    }

}
