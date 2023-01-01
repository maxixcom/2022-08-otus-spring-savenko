package io.github.maxixcom.otus.booklib.actuator;

import io.github.maxixcom.otus.booklib.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = BookCountHealthIndicator.class)
class BookCountHealthIndicatorTest {
    @Autowired
    private BookCountHealthIndicator healthIndicator;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void shouldBeInAGoodHealth() {
        Mockito.when(bookRepository.count()).thenReturn(10L);


        Health health = healthIndicator.health();

        Assertions.assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void shouldBeIll() {
        Mockito.when(bookRepository.count()).thenReturn(0L);

        Health health = healthIndicator.health();

        Assertions.assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        Assertions.assertThat(health.getDetails()).containsEntry("empty", "Library is empty");
    }
}