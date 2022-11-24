package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService authorService;

    @Test
    void shouldReturnAllGenres() throws Exception {
        List<GenreDto> genres = List.of(new GenreDto(1, "Genre"));
        Mockito.when(authorService.getAllGenres()).thenReturn(genres);

        String expectedResult = mapper.writeValueAsString(genres);

        mvc.perform(MockMvcRequestBuilders.get("/api/genre"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }
}