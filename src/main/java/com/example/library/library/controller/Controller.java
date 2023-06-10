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
    public String create(@RequestBody Book book){
        bookService.create(book);
        return "Book Created Successfully";
    }

    @GetMapping("/read")
    public List<Book> read(){
        return bookService.readAll();
    }

    @GetMapping("/read/{id}")
    public Book read(@PathVariable Long id){
        return bookService.read(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestBody Book book) {
        bookService.update(id, book);
        return "Book Updated Successfully";
    }

    @DeleteMapping("/delete")
    public String delete(@PathVariable Long id){
        bookService.delete(id);
        return "Book Deleted Successfully";
    }
}
