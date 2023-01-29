package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @CircuitBreaker(name = "getAllGenres", fallbackMethod = "fallbackGetAllGenres")
    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreDto::fromDomainObject)
                .collect(Collectors.toList());
    }

    List<AuthorDto> fallbackGetAllGenres(Exception exception) {
        return Collections.emptyList();
    }
}
