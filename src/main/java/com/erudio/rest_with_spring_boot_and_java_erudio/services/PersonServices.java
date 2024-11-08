package com.erudio.rest_with_spring_boot_and_java_erudio.services;

import com.erudio.rest_with_spring_boot_and_java_erudio.controllers.PersonController;
import com.erudio.rest_with_spring_boot_and_java_erudio.exceptions.ResourceNotFoundException;
import com.erudio.rest_with_spring_boot_and_java_erudio.model.Person;
import com.erudio.rest_with_spring_boot_and_java_erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){
    	
    	List<Person> persons = repository.findAll();
    	persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
    	
        return persons;
    }

    public Person findById(Long id){
    	
    	var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    	entity.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    	return entity;
    }

    public Person create(Person person){
    	var entity = repository.save(person);
    	entity.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
        return entity;
    }

    public Person update(Person person){

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        var updatedPerson = repository.save(person);
        
        updatedPerson.add(linkTo(methodOn(PersonController.class).findById(updatedPerson.getKey())).withSelfRel());

        return updatedPerson;
    }

    public void delete(Long id){

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
