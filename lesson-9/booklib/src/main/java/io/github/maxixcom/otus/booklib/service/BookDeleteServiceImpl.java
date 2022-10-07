package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dao.BookDao;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookDeleteServiceImpl implements BookDeleteService {
    private final BookDao bookDao;

    private final IOService ioService;

    @Override
    public void deleteBooks(Set<Long> bookIds) {
        bookDao.deleteByIds(bookIds);

        ioService.out(
                "Books #%s deleted%n",
                bookIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
