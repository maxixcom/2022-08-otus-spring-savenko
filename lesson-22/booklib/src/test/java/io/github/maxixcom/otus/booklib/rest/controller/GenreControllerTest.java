package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.service.GenreService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

@WebFluxTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnListOfGenres() throws JsonProcessingException {
        List<GenreDto> genres = List.of(new GenreDto(ObjectId.get().toHexString(), "Genre"));

        Mockito.when(genreService.getAllGenres()).thenReturn(Flux.fromIterable(genres));

        String expectedResult = mapper.writeValueAsString(genres);

        this.webClient.get().uri("/api/genre").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedResult);
    }
}