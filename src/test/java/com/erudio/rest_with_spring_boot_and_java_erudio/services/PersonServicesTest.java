package com.erudio.rest_with_spring_boot_and_java_erudio.services;

import com.erudio.rest_with_spring_boot_and_java_erudio.exceptions.RequiredObjectNullException;
import com.erudio.rest_with_spring_boot_and_java_erudio.model.Person;
import com.erudio.rest_with_spring_boot_and_java_erudio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @InjectMocks
    PersonServices services;

    @Mock
    PersonRepository repository;

    Person person;

    @BeforeEach
    void setUpMocks() {
        person = new Person();
        person.setKey(1L);
        person.setFirstName("First Name Test1");
        person.setLastName("Last Name Test1");
        person.setGender("Male");
        person.setAddress("Address Test1");
    }

    @Test
    void findAll() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Person person = new Person();
            person.setFirstName("Pessoa " + i);
            person.setLastName("Last Name " + i);
            person.setGender("Male");
            person.setAddress("Address " + i);
            list.add(person);
        }

        when(repository.findAll()).thenReturn(list);

        var people = services.findAll();

        assertNotNull(people);
        assertEquals(4, people.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Male", result.getGender());
    }


    @Test
    void create() {
        when(repository.save(person)).thenReturn(person);

        var result = services.create(person);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Male", result.getGender());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectNullException.class, () -> services.create(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    void update() {
        person.setFirstName("First Name Test2");
        person.setLastName("Last Name Test2");
        person.setGender("Female");
        person.setAddress("Address Test2");

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(person);

        var result = services.update(person);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/1>;rel=\"self\"]"));
        assertEquals("Address Test2", result.getAddress());
        assertEquals("First Name Test2", result.getFirstName());
        assertEquals("Last Name Test2", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectNullException.class, () -> services.update(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void delete() {
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        services.delete(1L);
    }
}