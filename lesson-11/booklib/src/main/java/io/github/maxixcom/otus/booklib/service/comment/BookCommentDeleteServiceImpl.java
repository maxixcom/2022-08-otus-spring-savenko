package io.github.maxixcom.otus.booklib.service.comment;

import io.github.maxixcom.otus.booklib.repository.BookCommentRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookCommentDeleteServiceImpl implements BookCommentDeleteService {
    private final IOService ioService;
    private final BookCommentRepository bookCommentRepository;

    @Transactional
    @Override
    public void deleteComment(Set<Long> commentIds) {
        bookCommentRepository.deleteByIds(commentIds);

        ioService.out(
                "Book comments #%s deleted%n",
                commentIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", #"))
        );
    }
}
