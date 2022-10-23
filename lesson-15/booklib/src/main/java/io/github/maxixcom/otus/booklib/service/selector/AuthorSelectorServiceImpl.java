package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorSelectorServiceImpl implements AuthorSelectorService {
    private final IOService ioService;
    private final AuthorService authorService;

    @Override
    public Optional<Author> selectAuthor() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            ioService.out("No authors to select%n");
            return Optional.empty();
        }

        printList(authors);

        ObjectId id = new ObjectId(ioService.readLineWithPrompt("%nSelect author id: "));
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findAny();
    }

    private void printList(List<Author> authors) {
        authors.forEach(author ->
                ioService.out("%s : %s%n",
                        author.getId(),
                        author.getName()
                )
        );
    }
}
