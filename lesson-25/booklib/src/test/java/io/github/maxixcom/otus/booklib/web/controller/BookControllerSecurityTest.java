
package io.github.maxixcom.otus.booklib.web.controller;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.maxixcom.otus.booklib.service.BookService;
import io.github.maxixcom.otus.booklib.service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(BookController.class)
class BookControllerSecurityTest {
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

    @Test
    @WithMockUser
    void listPageIsAvailableForAuthenticatedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createBookPageRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void createBookActionRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .param("title", "title_1")
                        .param("authorId", "1")
                        .param("genreId", "2")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void createBookActionIsAvailableForAuthenticatedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/create")
                        .param("title", "title_1")
                        .param("authorId", "1")
                        .param("genreId", "2")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void editBookPageRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/edit").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void editBookPageIsAvailableForAuthenticatedUser() throws Exception {
        Mockito.when(bookService.getBookByIdForUpdate(1))
                .thenReturn(Optional.of(new UpdateBookDto(1, "title_1", null, null)));

        mvc.perform(MockMvcRequestBuilders.get("/edit").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void editBookActionRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/edit")
                        .param("id", "1")
                        .param("title", "title_1_updated")
                        .param("authorId", "1")
                        .param("genreId", "2")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void editBookActionIsAvailableForAuthenticatedUser() throws Exception {
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
    }


    @Test
    void deleteBookPageRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/delete"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void deleteBookPageIaAvailableForAuthenticatedUser() throws Exception {
        Mockito.when(bookService.getBookById(1))
                .thenReturn(Optional.of(new BookDto(1, "title_1", null, null)));

        mvc.perform(MockMvcRequestBuilders.get("/delete").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteBookActionRequiresAuthentication() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/delete").param("id", "1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @WithMockUser
    @Test
    void deleteBookActionIsAvailableForAuthenticatedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/delete").param("id", "1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }
}