package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll(Sort.by("title"));
    }
}
