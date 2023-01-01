package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAllWithAuthorAndGenres()
                .stream()
                .map(BookDto::fromDomainObject)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public long createBook(CreateBookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(
                Optional.ofNullable(dto.getAuthorId())
                        .flatMap(authorRepository::findById)
                        .orElse(null)
        );
        book.setGenre(
                Optional.ofNullable(dto.getGenreId())
                        .flatMap(genreRepository::findById)
                        .orElse(null)
        );

        bookRepository.save(book);

        return book.getId();
    }

    @Transactional
    @Override
    public BookDto updateBook(long id, UpdateBookDto dto) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(dto.getTitle());
                    book.setAuthor(
                            Optional.ofNullable(dto.getAuthorId())
                                    .flatMap(authorRepository::findById)
                                    .orElse(null)
                    );
                    book.setGenre(
                            Optional.ofNullable(dto.getGenreId())
                                    .flatMap(genreRepository::findById)
                                    .orElse(null)
                    );
                    return book;
                })
                .map(bookRepository::save)
                .map(BookDto::fromDomainObject)
                .orElseThrow(() -> new BookNotFoundException("Book " + id + " not found"));
    }

    @Transactional
    @Override
    public void deleteBooks(Set<Long> bookIds) {
        bookRepository.deleteAllByIdInBatch(bookIds);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> getBookById(long id) {
        return bookRepository.findBookWithAuthorAndGenreById(id).map(BookDto::fromDomainObject);
    }

}
