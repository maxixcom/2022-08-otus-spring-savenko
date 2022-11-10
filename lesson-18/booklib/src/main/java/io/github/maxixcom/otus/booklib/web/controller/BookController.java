package io.github.maxixcom.otus.booklib.web.controller;

import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.maxixcom.otus.booklib.service.GenreService;
import io.github.maxixcom.otus.booklib.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    String list(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "list";
    }

    @GetMapping("/create")
    String create(Model model) {
        model.addAllAttributes(
                Map.of(
                        "book", new CreateBookDto(),
                        "authors", authorService.getAllAuthors(),
                        "genres", genreService.getAllGenres()
                )
        );
        return "create";
    }

    @PostMapping("/create")
    String createBook(@Valid @ModelAttribute("book") CreateBookDto createBookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(Map.of(
                    "authors", authorService.getAllAuthors(),
                    "genres", genreService.getAllGenres()
            ));
            return "create";
        }
        bookService.createBook(createBookDto);
        return "redirect:/";
    }

    @GetMapping("/edit")
    String edit(@RequestParam("id") long id, Model model) {
        model.addAllAttributes(
                Map.of(
                        "book", bookService.getBookByIdForUpdate(id).orElseThrow(BookNotFoundException::new),
                        "authors", authorService.getAllAuthors(),
                        "genres", genreService.getAllGenres()
                )
        );
        return "edit";
    }

    @PostMapping("/edit")
    String editBook(@Valid @ModelAttribute("book") UpdateBookDto updateBookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(Map.of(
                    "authors", authorService.getAllAuthors(),
                    "genres", genreService.getAllGenres()
            ));
            return "edit";
        }

        bookService.updateBook(updateBookDto);
        return "redirect:/";
    }

    @GetMapping("/delete")
    String delete(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id).orElseThrow(BookNotFoundException::new));
        return "delete";
    }

    @PostMapping("/delete")
    String deleteBook(@RequestParam("id") long id) {
        bookService.deleteBooks(Set.of(id));
        return "redirect:/";
    }
}
