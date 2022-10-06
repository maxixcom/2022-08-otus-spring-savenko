package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.dao.AuthorDao;
import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorSelectorServiceImpl implements AuthorSelectorService {
    private final IOService ioService;
    private final AuthorDao authorDao;

    @Override
    public Optional<Author> selectAuthor() {
        List<Author> authors = authorDao.findAll();
        if (authors.isEmpty()) {
            ioService.out("No authors to select%n");
            return Optional.empty();
        }

        printList(authors);

        int id = ioService.readIntWithPrompt("%nSelect author id: ");

        return authors.stream()
                .filter(author -> author.getId() == id)
                .findAny();
    }

    private void printList(List<Author> authors) {
        authors.forEach(author ->
                ioService.out("%d. %s%n",
                        author.getId(),
                        author.getName()
                )
        );
    }
}
