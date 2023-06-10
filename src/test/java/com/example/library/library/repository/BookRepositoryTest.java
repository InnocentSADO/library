package com.example.library.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.library.library.modele.Book;
import com.example.library.library.service.BookService;

public class BookRepositoryTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookRepository bookRepository;
    Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L,"Title 1","Author 1", 1985,"xx-xxxx-xxxx-xx");
        bookRepository.save(book);

    }

    @AfterEach
    void tearDown() {
        book = null;
        bookRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindBookByIsbn_Found(){
        Optional<Book> bookOptional = bookRepository.findBookByIsbn("xx-xxxx-xxxx-xx");
        assertTrue(bookOptional.isPresent());
        assertEquals(book.getId(), bookOptional.get().getId());
        assertEquals(book.getTitle(), bookOptional.get().getTitle());
        assertEquals(book.getPublicationYear(), bookOptional.get().getPublicationYear());
    }

    // Test case FAILURE
    @Test
    void testFindBookByIsbn_NotFound()
    {
        Optional<Book> bookList = BookRepository.findBookByIsbn("gfgh");
        assertThat(bookList.isEmpty()).isTrue();
    }
}
