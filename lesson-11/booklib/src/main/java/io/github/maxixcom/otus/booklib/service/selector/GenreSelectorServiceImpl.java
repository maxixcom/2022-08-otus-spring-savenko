package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreSelectorServiceImpl implements GenreSelectorService {
    private final IOService ioService;
    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> selectGenre() {
        List<Genre> genres = genreRepository.findAll();
        if (genres.isEmpty()) {
            ioService.out("No genres to select%n");
            return Optional.empty();
        }

        printList(genres);

        int id = ioService.readIntWithPrompt("%nSelect genre id: ");

        return genres.stream()
                .filter(author -> author.getId() == id)
                .findAny();
    }


    private void printList(List<Genre> authors) {
        authors.forEach(author ->
                ioService.out("%d. %s%n",
                        author.getId(),
                        author.getTitle()
                )
        );
    }
}
