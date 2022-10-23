package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.selector.AuthorSelectorService;
import io.github.maxixcom.otus.booklib.service.selector.GenreSelectorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest
class BookCommandServiceImplTest {
    private static final String NEW_BOOK_TITLE = "New Book";
    @Autowired
    private BookCommandService bookCommandService;
    @MockBean
    private IOService ioService;
    @Autowired
    private BookInteraction bookInteraction;
    @Autowired
    private AuthorSelectorService authorSelectorService;
    @Autowired
    private GenreSelectorService genreSelectorService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(NEW_BOOK_TITLE));
        mongoTemplate.findAllAndRemove(query, Book.class);
    }

    @Test
    void createBook() {
        Mockito.when(ioService.readLineWithPrompt(Mockito.any()))
                .thenReturn(NEW_BOOK_TITLE)
                .thenReturn("\n")
                .thenReturn("\n")
                .thenReturn("\n");

        bookCommandService.createBook();

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(NEW_BOOK_TITLE));
        Book actualBook = mongoTemplate.findOne(query, Book.class);

        Assertions.assertThat(actualBook).isNotNull();
    }
}