package com.example.library.library.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.library.library.modele.Book;
import com.example.library.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.qos.logback.core.net.ObjectWriter;

public class ControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    Book bookOne;
    Book bookTwo;
    List<Book> bookList= new ArrayList<>();

    @BeforeEach
    void setUp() {
        bookOne = new Book(1L,"Title 1","Author 1", 1985,"xx-xxxx-xxxx-xx");
        bookTwo = new Book(1L,"Title 2","Author 2", 1990,"xx-xxxx-xxxx-xx");
        bookList.add(bookOne);
        bookList.add(bookTwo);
        
        // Assertions
        assertNotNull(bookOne);
        assertNotNull(bookTwo);
        assertEquals(2, bookList.size());
        assertTrue(bookList.contains(bookOne));
        assertTrue(bookList.contains(bookTwo));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBookDetails() throws Exception {
        when(bookService.read(1L)).thenReturn(bookOne);
        
        this.mockMvc.perform(get("/book/" + "1L"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.title").value("Title 1"))
                    .andExpect(jsonPath("$.author").value("Author 1"))
                    .andExpect(jsonPath("$.year").value(1985))
                    .andExpect(jsonPath("$.isbn").value("xx-xxxx-xxxx-xx"));
    }

    @Test
    void readAll() throws  Exception {
        when(bookService.readAll()).thenReturn(bookList);
        
        this.mockMvc.perform(get("/book"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].title").value("Title 1"))
                    .andExpect(jsonPath("$[0].author").value("Author 1"))
                    .andExpect(jsonPath("$[0].year").value(1985))
                    .andExpect(jsonPath("$[0].isbn").value("xx-xxxx-xxxx-xx"))
                    .andExpect(jsonPath("$[1].id").value(1))
                    .andExpect(jsonPath("$[1].title").value("Title 2"))
                    .andExpect(jsonPath("$[1].author").value("Author 2"))
                    .andExpect(jsonPath("$[1].year").value(1990))
                    .andExpect(jsonPath("$[1].isbn").value("xx-xxxx-xxxx-xx"));
    }

    

    @Test
    void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(bookOne);

        when(bookService.create(bookOne)).thenReturn("Success");
        MvcResult mvcResult = this.mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andDo(print()).andExpect(status().isOk())
                        .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Success", response);
    }

    @Test
    void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(bookOne);

        when(bookService.update(bookOne.getId(),bookOne))
                .thenReturn("Book Updated Successfully");
        MvcResult mvcResult = this.mockMvc.perform(put("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Book Updated Successfully", response);
    }

    @Test
    void delete() throws Exception {
        when(bookService.delete("1L"))
                .thenReturn("Book Deleted Successfully");
        MvcResult mvcResult = this.mockMvc.perform(delete("/book/" + "1L"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Book Deleted Successfully", response);
    }
}
