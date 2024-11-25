package com.erudio.rest_with_spring_boot_and_java_erudio.repositories;

import com.erudio.rest_with_spring_boot_and_java_erudio.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
