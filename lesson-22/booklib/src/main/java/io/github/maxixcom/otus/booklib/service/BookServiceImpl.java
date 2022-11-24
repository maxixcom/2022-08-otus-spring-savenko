package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAllOrderedByTitle()
                .map(BookDto::fromDomainObject);
    }

    @Override
    public Mono<Void> deleteBooks(Set<String> bookIds) {
        return bookRepository.deleteAllById(bookIds);
    }

    @Override
    public Mono<BookDto> getBookById(String id) {
        return bookRepository.findById(id)
                .map(BookDto::fromDomainObject);
    }

    @Override
    public Mono<String> createBook(Mono<CreateBookDto> dto) {
        return dto
                .flatMap(createBookDto -> {
                    Book book = new Book(null, createBookDto.getTitle(), null, null);

                    return updateBookWithAuthorAndGenre(
                            Mono.just(book),
                            getAuthorByIdOrCreateNew(createBookDto.getAuthorId()),
                            getGenreByIdOrCreateNew(createBookDto.getGenreId())
                    );
                })
                .flatMap(bookRepository::save)
                .map(Book::getId);
    }

    @Override
    public Mono<BookDto> updateBook(String id, Mono<UpdateBookDto> dto) {
        return Mono.zip(bookRepository.findById(id), dto)
                .flatMap(data -> {
                    Book book = data.getT1();
                    UpdateBookDto updateBookDto = data.getT2();

                    book.setTitle(updateBookDto.getTitle());

                    return updateBookWithAuthorAndGenre(
                            Mono.just(book),
                            getAuthorByIdOrCreateNew(updateBookDto.getAuthorId()),
                            getGenreByIdOrCreateNew(updateBookDto.getGenreId())
                    );
                })
                .flatMap(bookRepository::save)
                .map(BookDto::fromDomainObject);
    }

    private Mono<Author> getAuthorByIdOrCreateNew(String authorId) {
        return authorRepository.findById(Mono.justOrEmpty(authorId))
                .defaultIfEmpty(new Author());
    }

    private Mono<Genre> getGenreByIdOrCreateNew(String genreId) {
        return genreRepository.findById(Mono.justOrEmpty(genreId))
                .defaultIfEmpty(new Genre());
    }

    private Mono<Book> updateBookWithAuthorAndGenre(Mono<Book> bookMono, Mono<Author> authorMono, Mono<Genre> genreMono) {
        return Mono
                .zip(bookMono, authorMono, genreMono)
                .map(data -> {
                    Book book = data.getT1();
                    Author author = Optional.of(data.getT2())
                            .filter(o -> o.getId() != null)
                            .orElse(null);

                    Genre genre = Optional.of(data.getT3())
                            .filter(o -> o.getId() != null)
                            .orElse(null);

                    book.setAuthor(author);
                    book.setGenre(genre);
                    return book;
                });
    }
}
