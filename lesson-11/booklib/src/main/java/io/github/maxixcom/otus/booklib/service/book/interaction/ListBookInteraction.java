package io.github.maxixcom.otus.booklib.service.book.interaction;

import io.github.maxixcom.otus.booklib.domain.Book;

import java.util.List;

public interface ListBookInteraction {
    void listBooks(List<Book> books);
}
