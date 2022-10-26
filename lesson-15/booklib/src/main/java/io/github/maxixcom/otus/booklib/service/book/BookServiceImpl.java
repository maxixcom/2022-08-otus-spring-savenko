package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.CommentRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
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
    private final CommentRepository commentRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll(Sort.by("title"));
    }

    @Transactional
    @Override
    public Book createBook(CreateBookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(UpdateBookDto dto) {

        return bookRepository.findById(IdUtils.stringToObjectId(dto.getBookId()))
                .map(book -> {
                    book.setTitle(dto.getTitle());
                    book.setAuthor(dto.getAuthor());
                    book.setGenre(dto.getGenre());
                    return book;
                })
                .map(bookRepository::save)
                .orElseThrow(() -> new BookNotFoundException("Book " + dto.getBookId() + " not found"));
    }

    @Transactional
    @Override
    public void deleteBooks(Set<String> bookIds) {
        List<ObjectId> ids = bookIds.stream()
                .map(IdUtils::stringToObjectId)
                .collect(Collectors.toList());
        bookRepository.deleteAllById(ids);
        commentRepository.deleteAllByBookIdIn(ids);
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(IdUtils.stringToObjectId(id));
    }
}
