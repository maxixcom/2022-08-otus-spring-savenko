package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;

import java.util.List;

public interface BookInteraction {
    CreateBookDto collectBookCreateInfo();

    UpdateBookDto collectBookUpdateInfo(Book book);

    void listBooks(List<Book> books);
}
