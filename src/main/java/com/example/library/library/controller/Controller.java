package com.example.library.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.library.modele.Book;
import com.example.library.library.service.BookService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/book")
@AllArgsConstructor

public class Controller {
    private final BookService bookService;

    @PostMapping("/create")
    public Book create(@RequestBody Book book){
        return bookService.create(book);
    }

    @GetMapping("/read")
    public List<Book> read(){
        return bookService.read();
    }

    @PutMapping("/update/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/delete")
    public String delete(@PathVariable Long id){
        return bookService.delete(id);
    }
}
