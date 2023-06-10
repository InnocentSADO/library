package com.example.library.library.service;
import java.util.List;
import java.util.Optional;

import com.example.library.library.modele.Book;

public interface BookDao {
    public Book create(Book book);
    
    public List<Book> readAll();

    public Book read(Long bookId);
    
    public Book update(Long id, Book book);
    
    public String delete(Long id);

    Optional<Book> findBookByIsbn(String bookIsbn);
}
