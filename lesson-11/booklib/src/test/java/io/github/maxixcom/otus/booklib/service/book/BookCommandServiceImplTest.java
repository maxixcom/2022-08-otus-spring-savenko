package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.book.interaction.CreateBookInteraction;
import io.github.maxixcom.otus.booklib.service.book.interaction.ListBookInteraction;
import io.github.maxixcom.otus.booklib.service.book.interaction.UpdateBookInteraction;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class BookCommandServiceImplTest {
    @Autowired
    private BookCommandService bookCommandService;
    @Autowired
    private BookRepository bookRepository;
    @MockBean
    private IOService ioService;
    @MockBean
    private ListBookInteraction listBookInteraction;
    @Autowired
    private CreateBookInteraction createBookInteraction;
    @MockBean
    private UpdateBookInteraction updateBookInteraction;
    @MockBean
    private AuthorSelectorService authorSelectorService;
    @MockBean
    private GenreSelectorService genreSelectorService;

    @Test
    void createBook() {
        Mockito.when(ioService.readLineWithPrompt(Mockito.any()))
                .thenReturn("New Book")
                .thenReturn("\n")
                .thenReturn("\n")
                .thenReturn("\n");

        bookCommandService.createBook();

        Optional<Book> bookOptional = bookRepository.findById(100);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b.getTitle()).isEqualTo("New Book");
                });
    }
}