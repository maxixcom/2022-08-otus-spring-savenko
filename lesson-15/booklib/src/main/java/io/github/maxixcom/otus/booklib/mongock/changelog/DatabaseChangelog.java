package io.github.maxixcom.otus.booklib.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "maxix", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertInitialData", author = "maxix")
    public void insertInitialData(
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookRepository bookRepository
    ) {
        List<String> authorNames = List.of(
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

        List<Author> authors = authorNames.stream()
                .map(name -> new Author(null, name))
                .map(authorRepository::save)
                .collect(Collectors.toList());

        List<String> genreNames = List.of(
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

        List<Genre> genres = genreNames.stream()
                .map(name -> new Genre(null, name))
                .map(genreRepository::save)
                .collect(Collectors.toList());


        Map<String, List<String>> bookData = new HashMap<>();
        bookData.put("Капитанская дочка", List.of("Александр Пушкин", "Классика"));
        bookData.put("Хамелеон", List.of("Антон Чехов", "Классика"));
        bookData.put("Гарри Поттер", List.of("Джоан Роулинг", "Фэнтези"));
        bookData.put("Приключения Шерлока Холмса", List.of("Артур Конан Дойль", "Детектив"));
        bookData.put("Война и мир", List.of("Лев Толстой", "Классика"));
        bookData.put("Остров доктора Моро", List.of("Герберт Уэллс", "Фантастика"));
        bookData.put("Властелин колец", List.of("Толкиен", "Фэнтези"));
        bookData.put("Противостояние", List.of("Стивен Кинг", "Фантастика"));
        bookData.put("Преступление и наказание", List.of("Федор Достоевский", "Классика"));
        bookData.put("Три мушкетёра", List.of("Александр Дюма", "Приключения"));

        List<Book> books = bookData.entrySet().stream()
                .map(data -> {
                    Book book = new Book();
                    book.setTitle(data.getKey());
                    book.setAuthor(findAuthorByNameOrNull(data.getValue().get(0), authors));
                    book.setGenre(findGenreByTitleOrNull(data.getValue().get(1), genres));
                    return book;
                })
                .collect(Collectors.toList());

        bookRepository.saveAll(books);
    }

    private Author findAuthorByNameOrNull(String name, List<Author> authors) {
        return authors.stream()
                .filter(author -> name.equals(author.getName()))
                .findFirst()
                .orElse(null);
    }

    private Genre findGenreByTitleOrNull(String title, List<Genre> genres) {
        return genres.stream()
                .filter(genre -> title.equals(genre.getTitle()))
                .findFirst()
                .orElse(null);
    }

}
