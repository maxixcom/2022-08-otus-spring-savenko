package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCreateServiceImpl implements BookCreateService {
    private final IOService ioService;
    private final BookRepository bookRepository;
    private final AuthorSelectorService authorSelectorService;
    private final GenreSelectorService genreSelectorService;

    @Transactional
    @Override
    public void createBook() {
        Book book = collectBookInfo();

        book = bookRepository.save(book);

        ioService.out("%nBook #%d created%n", book.getId());
    }

    private Book collectBookInfo() {
        Book book = new Book();

        book.setTitle(inputBookTitle());
        book.setAuthor(selectBookAuthor().orElse(null));
        book.setGenre(selectBookGenre().orElse(null));

        return book;
    }

    private String inputBookTitle() {
        while (true) {
            String newTitle = ioService.readLineWithPrompt("Title: ");
            if (!newTitle.isBlank()) {
                return newTitle;
            }
        }
    }

    private Optional<Author> selectBookAuthor() {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt("Author (skip - enter, l - for select): ");

            if (selectCommand.equals("l")) {
                return authorSelectorService.selectAuthor();
            }

            if (selectCommand.isBlank()) {
                return Optional.empty();
            }
        }
    }

    private Optional<Genre> selectBookGenre() {
        while (true) {
            String selectCommand = ioService.readLineWithPrompt("Genre (skip - enter, l - for select): ");

            if (selectCommand.equals("l")) {
                return genreSelectorService.selectGenre();
            }

            if (selectCommand.isBlank()) {
                return Optional.empty();
            }
        }
    }
}
