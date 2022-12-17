package io.github.maxixcom.otus.booklib.web.controller;


import io.github.maxixcom.otus.booklib.config.SecurityConfig;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.maxixcom.otus.booklib.service.BookService;
import io.github.maxixcom.otus.booklib.service.GenreService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(BookController.class)
public class BookControllerAuthTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    WebInvocationPrivilegeEvaluator evaluator;

    private static Map<Endpoints, MockHttpServletRequestBuilder> EndpointBuilders;

    private enum Endpoints {
        BookListPage,
        BookCreatePage,
        BookCreate,
        BookEditPage,
        BookEdit,
        BookDeletePage,
        BookDelete
    }

    @BeforeAll
    static void beforeAll() {
        EndpointBuilders = new HashMap<>();
        EndpointBuilders.put(Endpoints.BookListPage, get("/"));
        EndpointBuilders.put(Endpoints.BookCreatePage, get("/create"));
        EndpointBuilders.put(Endpoints.BookCreate, post("/create")
                .param("title", "title_1")
                .param("authorId", "1")
                .param("genreId", "2")
                .with(csrf()));
        EndpointBuilders.put(Endpoints.BookEditPage, get("/edit")
                .param("id", "1"));
        EndpointBuilders.put(Endpoints.BookEdit, post("/edit")
                .param("id", "1")
                .param("title", "title_1_updated")
                .param("authorId", "1")
                .param("genreId", "2")
                .with(csrf()));
        EndpointBuilders.put(Endpoints.BookDeletePage, get("/delete")
                .param("id", "1"));
        EndpointBuilders.put(Endpoints.BookDelete, post("/delete")
                .param("id", "1")
                .with(csrf()));
    }

    @BeforeEach
    void setUp() {
        Mockito.when(bookService.getBookByIdForUpdate(1))
                .thenReturn(Optional.of(new UpdateBookDto(1, "title_1", null, null)));

        Mockito.when(bookService.getBookById(1))
                .thenReturn(Optional.of(new BookDto(1, "title_1", null, null)));
    }

    private static Stream<Arguments> provideArgumentsForTestUserRoleAuthorization() {
        return Stream.of(
                Arguments.of(EndpointBuilders.get(Endpoints.BookListPage), status().isOk()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreatePage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreate), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEditPage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEdit), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDeletePage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDelete), status().isForbidden())
        );
    }

    @WithMockUser
    @ParameterizedTest
    @MethodSource("provideArgumentsForTestUserRoleAuthorization")
    void testUserRoleAuthorization(MockHttpServletRequestBuilder builders, ResultMatcher resultMatchers) throws Exception {
        mvc.perform(builders).andExpect(resultMatchers);
    }

    private static Stream<Arguments> provideArgumentsForTestAdminRoleAuthorization() {
        return Stream.of(
                Arguments.of(EndpointBuilders.get(Endpoints.BookListPage), status().isOk()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreatePage), status().isOk()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreate), status().is3xxRedirection()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEditPage), status().isOk()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEdit), status().is3xxRedirection()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDeletePage), status().isOk()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDelete), status().is3xxRedirection())
        );
    }

    @WithMockUser(
            roles = {"ADMIN"}
    )
    @ParameterizedTest
    @MethodSource("provideArgumentsForTestAdminRoleAuthorization")
    void testAdminRoleAuthorization(MockHttpServletRequestBuilder builders, ResultMatcher resultMatchers) throws Exception {
        mvc.perform(builders).andExpect(resultMatchers);
    }


    private static Stream<Arguments> provideArgumentsForTestOtherRoleAuthorization() {
        return Stream.of(
                Arguments.of(EndpointBuilders.get(Endpoints.BookListPage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreatePage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookCreate), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEditPage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookEdit), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDeletePage), status().isForbidden()),
                Arguments.of(EndpointBuilders.get(Endpoints.BookDelete), status().isForbidden())
        );
    }

    @WithMockUser(
            roles = {"XXX"}
    )
    @ParameterizedTest
    @MethodSource("provideArgumentsForTestOtherRoleAuthorization")
    void testOtherRoleAuthorization(MockHttpServletRequestBuilder builders, ResultMatcher resultMatchers) throws Exception {
        mvc.perform(builders).andExpect(resultMatchers);
    }
}
