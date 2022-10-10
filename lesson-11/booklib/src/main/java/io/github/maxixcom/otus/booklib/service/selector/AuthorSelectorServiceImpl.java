package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorSelectorServiceImpl implements AuthorSelectorService {
    private final IOService ioService;
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> selectAuthor() {
        List<Author> authors = authorRepository.findAll();
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
