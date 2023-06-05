package com.example.library.library.service;
import java.util.List;

import com.example.library.library.modele.Book;

public interface BookDao {
    public Book create(Book book);
    
    public List<Book> read();
    
    public Book update(Long id, Book book);
    
    public String delete(Long id);
}
