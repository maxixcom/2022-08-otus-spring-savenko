package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();
}
