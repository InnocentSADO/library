package com.example.library.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.library.library.exception.BookAlreadyExistException;
import com.example.library.library.exception.BookNotFoundException;
import com.example.library.library.modele.Book;
import com.example.library.library.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService implements BookDao{

    private final BookRepository bookRepository;

    @Override
    public Book create(Book book) throws BookAlreadyExistException {
        String isbn = book.getIsbn();
        Optional<Book> existingBook = bookRepository.findBookByIsbn(isbn);
        if (existingBook.isPresent()) 
            throw new BookAlreadyExistException("Book with ISBN " + isbn + " already exists");
        
        return bookRepository.save(book);
    }

    @Override
    public List<Book> readAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book update(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        
        optionalBook.ifPresent(b -> {
            b.setAuthor(book.getAuthor());
            b.setIsbn(book.getIsbn());
            b.setPublicationYear(book.getPublicationYear());
            b.setTitle(book.getTitle());
            bookRepository.save(b);
        });
        return optionalBook.orElseThrow(() -> new RuntimeException("Book not Found!"));
    }

    @Override
    public String delete(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return "Book deleted!";
        } else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    @Override
    public Optional<Book> findBookByIsbn(String bookIsbn) {
        return bookRepository.findBookByIsbn(bookIsbn);
        
    }

    @Override
    public Book read(Long bookId) {
        return bookRepository.getReferenceById(bookId);
    }
    
}
