package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorDto::fromDomainObject)
                .collect(Collectors.toList());
    }
}
