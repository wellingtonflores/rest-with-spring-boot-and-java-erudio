package com.erudio.rest_with_spring_boot_and_java_erudio.services;

import com.erudio.rest_with_spring_boot_and_java_erudio.controllers.BookController;
import com.erudio.rest_with_spring_boot_and_java_erudio.controllers.PersonController;
import com.erudio.rest_with_spring_boot_and_java_erudio.exceptions.RequiredObjectNullException;
import com.erudio.rest_with_spring_boot_and_java_erudio.exceptions.ResourceNotFoundException;
import com.erudio.rest_with_spring_boot_and_java_erudio.model.Book;
import com.erudio.rest_with_spring_boot_and_java_erudio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;

    public List<Book> findAll(){
    	
    	List<Book> books = repository.findAll();
    	books.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
    	
        return books;
    }

    public Book findById(Long id){
    	
    	var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    	entity.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    	return entity;
    }

    public Book create(Book book){
        if(book == null) throw new RequiredObjectNullException();
    	var entity = repository.save(book);
    	entity.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
        return entity;
    }

    public Book update(Book book){
        if(book == null) throw new RequiredObjectNullException();

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        
        var updatedBook = repository.save(book);
        
        updatedBook.add(linkTo(methodOn(BookController.class).findById(updatedBook.getKey())).withSelfRel());

        return updatedBook;
    }

    public void delete(Long id){

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
