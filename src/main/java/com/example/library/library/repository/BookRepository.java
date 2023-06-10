package com.example.library.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.library.modele.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findBookByIsbn(String bookIsbn);
    
}
