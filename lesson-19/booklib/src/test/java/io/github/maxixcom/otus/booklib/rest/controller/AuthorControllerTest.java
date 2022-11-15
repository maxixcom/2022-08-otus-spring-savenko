package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnAllAuthors() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1, "Author"));
        Mockito.when(authorService.getAllAuthors()).thenReturn(authors);

        String expectedResult = mapper.writeValueAsString(authors);

        mvc.perform(MockMvcRequestBuilders.get("/api/author"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }
}