package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.BookService;

public interface UpdateBookInteraction {
    BookService.UpdateBookDto collectBookUpdateInfo(Book book);
}
