package com.erudio.rest_with_spring_boot_and_java_erudio.services;

import com.erudio.rest_with_spring_boot_and_java_erudio.model.Book;
import com.erudio.rest_with_spring_boot_and_java_erudio.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {
    
    @InjectMocks
    BookServices services;
    
    @Mock
    BookRepository repository;
    
    Book book;

    @BeforeEach
    void setUpMocks() {
        book = new Book();
        book.setKey(1L);
        book.setTitle("First Title Test1");
        book.setPrice(BigDecimal.valueOf(25.50));
        book.setAuthor("First Author Test1");
        book.setLaunchDate(LocalDateTime.parse("2017-11-29T13:50:05"));
    }

    @Test
    void findAll() {
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Book book = new Book();
            book.setTitle("First Title Test" + i);
            book.setPrice(BigDecimal.valueOf(25.50));
            book.setAuthor("First Author Test" + i);
            book.setLaunchDate(LocalDateTime.parse("2017-11-29T13:50:05"));
            list.add(book);
        }

        when(repository.findAll()).thenReturn(list);

        var books = services.findAll();

        assertNotNull(books);
        assertEquals(4, books.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/1>;rel=\"self\"]"));
        assertEquals("First Title Test1", result.getTitle());
        assertEquals(BigDecimal.valueOf(25.50), result.getPrice());
        assertEquals("First Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.parse("2017-11-29T13:50:05"), result.getLaunchDate());
    }

    @Test
    void create() {
        when(repository.save(book)).thenReturn(book);

        var result = services.create(book);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/1>;rel=\"self\"]"));
        assertEquals("First Title Test1", result.getTitle());
        assertEquals(BigDecimal.valueOf(25.50), result.getPrice());
        assertEquals("First Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.parse("2017-11-29T13:50:05"), result.getLaunchDate());
    }

    @Test
    void update() {
        book.setTitle("First Title Test1");
        book.setPrice(BigDecimal.valueOf(25.50));
        book.setAuthor("First Author Test1");
        book.setLaunchDate(LocalDateTime.parse("2017-11-29T13:50:05"));

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(book);

        var result = services.update(book);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/1>;rel=\"self\"]"));
        assertEquals("First Title Test1", result.getTitle());
        assertEquals(BigDecimal.valueOf(25.50), result.getPrice());
        assertEquals("First Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.parse("2017-11-29T13:50:05"), result.getLaunchDate());
    }

    @Test
    void delete() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        services.delete(1L);
    }
}