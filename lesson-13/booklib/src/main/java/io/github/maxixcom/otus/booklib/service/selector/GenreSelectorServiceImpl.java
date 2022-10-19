package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.GenreService;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreSelectorServiceImpl implements GenreSelectorService {
    private final IOService ioService;
    private final GenreService genreService;

    @Override
    public Optional<Long> selectGenre() {
        List<Genre> genres = genreService.getAllGenres();
        if (genres.isEmpty()) {
            ioService.out("No genres to select%n");
            return Optional.empty();
        }

        printList(genres);

        int id = ioService.readIntWithPrompt("%nSelect genre id: ");

        return genres.stream()
                .filter(genre -> genre.getId() == id)
                .findAny()
                .map(Genre::getId);
    }


    private void printList(List<Genre> authors) {
        authors.forEach(genre ->
                ioService.out("%d. %s%n",
                        genre.getId(),
                        genre.getTitle()
                )
        );
    }
}
