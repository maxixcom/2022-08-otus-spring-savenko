package io.github.maxixcom.otus.booklib.service.selector;

import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.service.GenreService;
import io.github.maxixcom.otus.booklib.service.io.IOService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreSelectorServiceImpl implements GenreSelectorService {
    private final IOService ioService;
    private final GenreService genreService;

    @Override
    public Optional<Genre> selectGenre() {
        List<Genre> genres = genreService.getAllGenres();
        if (genres.isEmpty()) {
            ioService.out("No genres to select%n");
            return Optional.empty();
        }

        printList(genres);

        ObjectId id = new ObjectId(ioService.readLineWithPrompt("%nSelect genre id: "));

        return genres.stream()
                .filter(genre -> genre.getId().equals(id))
                .findAny();
    }


    private void printList(List<Genre> authors) {
        authors.forEach(genre ->
                ioService.out("%s : %s%n",
                        genre.getId(),
                        genre.getTitle()
                )
        );
    }
}
