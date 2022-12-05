package io.github.maxixcom.otus.booklib.web.controller;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.maxixcom.otus.booklib.service.BookService;
import io.github.maxixcom.otus.booklib.service.GenreService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void listPageRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnBookList() throws Exception {
        List<BookDto> bookDtoList = List.of(
                new BookDto(1, "title_1", null, null),
                new BookDto(1, "title_2", null, null)
        );
        Mockito.when(bookService.getAllBooks()).thenReturn(bookDtoList);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).containsSequence("<title>Book list</title>");
        Assertions.assertThat(content).containsSequence("title_1");
        Assertions.assertThat(content).containsSequence("title_2");
    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnCreatePage() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).containsSequence("<title>Create book</title>");
    }

    @WithMockUser(username = "user")
    @Test
    void shouldCreateBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .param("title", "title_1")
                        .param("authorId", "1")
                        .param("genreId", "2")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        ArgumentCaptor<CreateBookDto> captorParam = ArgumentCaptor.forClass(CreateBookDto.class);
        Mockito.verify(bookService).createBook(captorParam.capture());

        CreateBookDto dto = captorParam.getValue();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(dto.getTitle()).isEqualTo("title_1"),
                () -> Assertions.assertThat(dto.getAuthorId()).isEqualTo(1),
                () -> Assertions.assertThat(dto.getGenreId()).isEqualTo(2)
        );
    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnEditPage_404() throws Exception {
        Mockito.when(bookService.getBookByIdForUpdate(1))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/edit").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnEditPage() throws Exception {
        Mockito.when(bookService.getBookByIdForUpdate(1))
                .thenReturn(Optional.of(new UpdateBookDto(1, "title_1", null, null)));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/edit").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).containsSequence("<title>Edit book</title>");
    }

    @WithMockUser(username = "user")
    @Test
    void shouldEditBook() throws Exception {
        Mockito.when(bookService.getBookByIdForUpdate(1))
                .thenReturn(Optional.of(new UpdateBookDto(1, "title_1", null, null)));

        mvc.perform(MockMvcRequestBuilders
                        .post("/edit")
                        .param("id", "1")
                        .param("title", "title_1_updated")
                        .param("authorId", "1")
                        .param("genreId", "2")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        ArgumentCaptor<UpdateBookDto> captorParam = ArgumentCaptor.forClass(UpdateBookDto.class);
        Mockito.verify(bookService).updateBook(captorParam.capture());

        UpdateBookDto dto = captorParam.getValue();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(dto.getTitle()).isEqualTo("title_1_updated"),
                () -> Assertions.assertThat(dto.getAuthorId()).isEqualTo(1),
                () -> Assertions.assertThat(dto.getGenreId()).isEqualTo(2)
        );

    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnDeletePage_404() throws Exception {
        Mockito.when(bookService.getBookById(1))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/delete").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @WithMockUser(username = "user")
    @Test
    void shouldReturnDeletePage() throws Exception {
        Mockito.when(bookService.getBookById(1))
                .thenReturn(Optional.of(new BookDto(1, "title_1", null, null)));

        mvc.perform(MockMvcRequestBuilders.get("/delete").param("id", "1"))
                .andExpect(MockMvcResultMatchers.view().name("delete"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldDeleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/delete").param("id", "1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Set<Long>> captorParam = ArgumentCaptor.forClass(Set.class);
        Mockito.verify(bookService).deleteBooks(captorParam.capture());

        Assertions.assertThat(captorParam.getValue()).containsAll(Set.of(1L));
    }

}