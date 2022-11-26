package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAllOrderedByTitle()
                .map(GenreDto::fromDomainObject);
    }
}
