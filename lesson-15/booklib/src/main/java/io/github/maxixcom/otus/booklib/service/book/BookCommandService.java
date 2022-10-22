package io.github.maxixcom.otus.booklib.service.book;

import java.util.Set;

public interface BookCommandService {
    void listBooks();

    void createBook();

    void updateBook(long bookId);

    void deleteBooks(Set<Long> bookIds);
}
