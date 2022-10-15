package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Book createBook(CreateBookDto dto) {
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

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(UpdateBookDto dto) {
        return bookRepository.findById(dto.getBookId())
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
                .orElseThrow(() -> new BookNotFoundException("Book " + dto.getBookId() + " not found"));
    }

    @Transactional
    @Override
    public void deleteBooks(Set<Long> bookIds) {
        bookRepository.deleteByIds(bookIds);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findByIdWithAuthorAndGenre(id);
    }
}
