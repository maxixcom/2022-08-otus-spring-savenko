
package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        List<BookDto> books = List.of(new BookDto(1, "Book_1", null, null));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        String expectedResult = mapper.writeValueAsString(books);

        mvc.perform(MockMvcRequestBuilders.get("/api/book"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }

    @Test
    void shouldReturnBook() throws Exception {
        BookDto bookDto = new BookDto(1, "Book_1", null, null);
        Mockito.when(bookService.getBookById(Mockito.eq(1L))).thenReturn(Optional.of(bookDto));

        String expectedResult = mapper.writeValueAsString(bookDto);

        mvc.perform(MockMvcRequestBuilders.get("/api/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }

    @Test
    void shouldNotReturnAnyBook() throws Exception {
        Mockito.when(bookService.getBookById(Mockito.eq(1L))).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/book/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateBook() throws Exception {
        Mockito.when(bookService.createBook(Mockito.any())).thenReturn(1L);

        CreateBookDto expectedDto = new CreateBookDto("book_1", null, null);
        String requestBody = mapper.writeValueAsString(expectedDto);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/book")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/book/1"))
                .andExpect(MockMvcResultMatchers.content().string(""));

        ArgumentCaptor<CreateBookDto> captorParam = ArgumentCaptor.forClass(CreateBookDto.class);
        Mockito.verify(bookService).createBook(captorParam.capture());

        CreateBookDto actualDto = captorParam.getValue();

        Assertions.assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void shouldEditBook() throws Exception {
        BookDto expectedDto = new BookDto(1L, "title_New", null, null);
        Mockito.when(bookService.updateBook(Mockito.eq(1L), Mockito.any())).thenReturn(expectedDto);

        UpdateBookDto updateBookDto = new UpdateBookDto("title_New", null, null);
        String requestBody = mapper.writeValueAsString(updateBookDto);

        String expectedResult = mapper.writeValueAsString(expectedDto);

        mvc.perform(
                        MockMvcRequestBuilders.put("/api/book/1")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));

        ArgumentCaptor<Long> captorParamId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<UpdateBookDto> captorParamDto = ArgumentCaptor.forClass(UpdateBookDto.class);
        Mockito.verify(bookService).updateBook(captorParamId.capture(), captorParamDto.capture());

        Long actualId = captorParamId.getValue();
        UpdateBookDto actualDto = captorParamDto.getValue();

        Assertions.assertThat(actualId).isEqualTo(1L);
        Assertions.assertThat(actualDto).usingRecursiveComparison().isEqualTo(updateBookDto);
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/book/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));

        Mockito.verify(bookService, Mockito.times(1)).deleteBooks(Mockito.eq(Set.of(1L)));
    }
}