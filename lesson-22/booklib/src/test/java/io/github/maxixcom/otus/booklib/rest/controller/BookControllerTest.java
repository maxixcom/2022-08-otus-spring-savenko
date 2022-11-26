package io.github.maxixcom.otus.booklib.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.BookService;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Set;

@WebFluxTest(BookController.class)
class BookControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        List<BookDto> books = List.of(new BookDto(ObjectId.get().toHexString(), "Book_1", null, null));
        Mockito.when(bookService.getAllBooks()).thenReturn(Flux.fromIterable(books));

        String expectedResult = mapper.writeValueAsString(books);

        this.webClient.get().uri("/api/book").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedResult);
    }

    @Test
    void shouldReturnBook() throws Exception {
        String bookId = ObjectId.get().toHexString();
        BookDto bookDto = new BookDto(bookId, "Book_1", null, null);
        Mockito.when(bookService.getBookById(Mockito.eq(bookId))).thenReturn(Mono.just(bookDto));

        String expectedResult = mapper.writeValueAsString(bookDto);

        this.webClient.get().uri("/api/book/" + bookId).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedResult);
    }

    @Test
    void shouldNotReturnAnyBook() {
        String bookId = ObjectId.get().toHexString();
        Mockito.when(bookService.getBookById(Mockito.eq(bookId))).thenReturn(Mono.empty());

        this.webClient.get().uri("/api/book/" + bookId).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldDeleteBook() {
        String bookId = ObjectId.get().toHexString();
        Mockito.when(bookService.deleteBooks(Mockito.anySet())).thenReturn(Mono.empty());

        this.webClient.delete().uri("/api/book/" + bookId).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        Mockito.verify(bookService, Mockito.times(1)).deleteBooks(Mockito.eq(Set.of(bookId)));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCreateBook() throws Exception {
        String bookId = ObjectId.get().toHexString();
        Mockito.when(bookService.createBook(Mockito.any())).thenReturn(Mono.just(bookId));

        CreateBookDto expectedDto = new CreateBookDto("book_1", null, null);
        String requestBody = mapper.writeValueAsString(expectedDto);

        this.webClient.post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/api/book/" + bookId)
                .expectBody().isEmpty();

        ArgumentCaptor<Mono> captorParam = ArgumentCaptor.forClass(Mono.class);
        Mockito.verify(bookService).createBook(captorParam.capture());

        Mono<CreateBookDto> actualDtoMono = captorParam.getValue();

        StepVerifier
                .create(actualDtoMono)
                .assertNext(actualDto -> Assertions.assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto))
                .expectComplete()
                .verify();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldEditBook() throws Exception {
        String bookId = ObjectId.get().toHexString();
        BookDto expectedDto = new BookDto(bookId, "title_New", null, null);
        Mockito.when(bookService.updateBook(Mockito.eq(bookId), Mockito.any())).thenReturn(Mono.just(expectedDto));

        UpdateBookDto updateBookDto = new UpdateBookDto("title_New", null, null);
        String requestBody = mapper.writeValueAsString(updateBookDto);

        String expectedResult = mapper.writeValueAsString(expectedDto);

        this.webClient.put().uri("/api/book/"+bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedResult);

        ArgumentCaptor<String> captorParamId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Mono> captorParamDto = ArgumentCaptor.forClass(Mono.class);
        Mockito.verify(bookService).updateBook(captorParamId.capture(), captorParamDto.capture());

        String actualId = captorParamId.getValue();
        Assertions.assertThat(actualId).isEqualTo(bookId);

        Mono<UpdateBookDto> actualDtoMono = captorParamDto.getValue();
        StepVerifier
                .create(actualDtoMono)
                .assertNext(actualDto -> Assertions.assertThat(actualDto).usingRecursiveComparison().isEqualTo(updateBookDto))
                .expectComplete()
                .verify();
    }
}