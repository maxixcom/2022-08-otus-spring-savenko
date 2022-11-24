package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAllOrderedByName()
                .map(AuthorDto::fromDomainObject);
    }
}
