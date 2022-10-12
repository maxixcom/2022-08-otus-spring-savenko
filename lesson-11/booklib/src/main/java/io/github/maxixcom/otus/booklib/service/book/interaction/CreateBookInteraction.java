package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.service.book.BookService;

public interface CreateBookInteraction {
    BookService.CreateBookDto collectBookInfo();
}
