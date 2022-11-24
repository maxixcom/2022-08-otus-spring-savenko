package io.github.maxixcom.otus.booklib.utils;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConditionalOnProperty(value = "demo-data.load", havingValue = "true")
@Component
@RequiredArgsConstructor
@Log4j2
public class DemoData {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private final List<String> authorNames = List.of(
            "Александр Пушкин",
            "Лев Толстой",
            "Федор Достоевский",
            "Антон Чехов",
            "Стивен Кинг",
            "Александр Дюма",
            "Толкиен",
            "Джоан Роулинг",
            "Артур Конан Дойль",
            "Герберт Уэллс"
    );

    private final List<String> genreNames = List.of(
            "Ужасы",
            "Классика",
            "Фэнтези",
            "Приключения",
            "Детектив",
            "Мистика",
            "Фантастика",
            "Триллер",
            "История",
            "Биография"
    );

    Map<String, List<String>> bookData = new HashMap<>() {{
        put("Капитанская дочка", List.of("Александр Пушкин", "Классика"));
        put("Хамелеон", List.of("Антон Чехов", "Классика"));
        put("Гарри Поттер", List.of("Джоан Роулинг", "Фэнтези"));
        put("Приключения Шерлока Холмса", List.of("Артур Конан Дойль", "Детектив"));
        put("Война и мир", List.of("Лев Толстой", "Классика"));
        put("Остров доктора Моро", List.of("Герберт Уэллс", "Фантастика"));
        put("Властелин колец", List.of("Толкиен", "Фэнтези"));
        put("Противостояние", List.of("Стивен Кинг", "Фантастика"));
        put("Преступление и наказание", List.of("Федор Достоевский", "Классика"));
        put("Три мушкетёра", List.of("Александр Дюма", "Приключения"));
    }};

    @PostConstruct
    void init() {

        Flux<Author> saveAuthors = Flux.fromIterable(authorNames)
                .map(name -> new Author(null, name))
                .flatMap(authorRepository::save);

        Flux<Genre> saveGenres = Flux.fromIterable(genreNames)
                .map(name -> new Genre(null, name))
                .flatMap(genreRepository::save);

        Flux.concat(
                        deleteAll(),
                        saveAuthors(),
                        saveGenres(),
                        saveBooks()
                )
                .subscribe();
    }

    private Mono<Void> deleteAll() {
        return Flux.concat(
                authorRepository.deleteAll(),
                genreRepository.deleteAll(),
                bookRepository.deleteAll()
        ).then();
    }

    private Mono<Void> saveAuthors() {
        return Flux.fromIterable(authorNames)
                .map(name -> new Author(null, name))
                .flatMap(authorRepository::save)
                .then();
    }

    private Mono<Void> saveGenres() {
        return Flux.fromIterable(genreNames)
                .map(name -> new Genre(null, name))
                .flatMap(genreRepository::save)
                .then();
    }

    private Mono<Void> saveBooks() {
        return Flux.fromIterable(bookData.entrySet())
                .flatMap(entry -> {
                    String title = entry.getKey();
                    String authorName = entry.getValue().get(0);
                    String genreTitle = entry.getValue().get(1);

                    return Mono.zip(
                            authorRepository.findByName(authorName),
                            genreRepository.findByTitle(genreTitle)
                    ).map(data -> new Book(
                            null,
                            title,
                            data.getT1(),
                            data.getT2()
                    ));
                })
                .flatMap(bookRepository::save)
                .then();
    }
}
