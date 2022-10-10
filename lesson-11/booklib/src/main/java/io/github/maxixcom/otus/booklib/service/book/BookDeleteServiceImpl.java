package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookDeleteServiceImpl implements BookDeleteService {
    private final BookRepository bookRepository;
    private final IOService ioService;

    @Transactional
    @Override
    public void deleteBooks(Set<Long> bookIds) {
        bookRepository.deleteByIds(bookIds);

        ioService.out(
                "Books #%s deleted%n",
                bookIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
