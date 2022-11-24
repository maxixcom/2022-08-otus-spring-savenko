package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import reactor.core.publisher.Flux;

public interface AuthorService {
    Flux<AuthorDto> getAllAuthors();
}
