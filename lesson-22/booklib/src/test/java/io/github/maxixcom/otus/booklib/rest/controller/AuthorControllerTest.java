package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
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

@WebFluxTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnListOfAuthors() throws JsonProcessingException {
        List<AuthorDto> authors = List.of(new AuthorDto(ObjectId.get().toHexString(), "Author"));

        Mockito.when(authorService.getAllAuthors()).thenReturn(Flux.fromIterable(authors));

        String expectedResult = mapper.writeValueAsString(authors);

        this.webClient.get().uri("/api/author").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedResult);
    }
}