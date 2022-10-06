package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dao.BookDao;
import io.github.maxixcom.otus.booklib.domain.Book;
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
class BookCreateServiceImplTest {
    @Autowired
    private BookCreateServiceImpl bookCreateService;
    @Autowired
    private BookDao bookDao;
    @MockBean
    private IOService ioService;
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

        bookCreateService.createBook();

        Optional<Book> bookOptional = bookDao.findById(100);

        Assertions.assertThat(bookOptional)
                .isPresent()
                .containsInstanceOf(Book.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b.getTitle()).isEqualTo("New Book");
                });
    }
}