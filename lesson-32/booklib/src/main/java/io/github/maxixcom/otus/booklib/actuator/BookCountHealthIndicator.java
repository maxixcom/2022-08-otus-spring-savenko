package io.github.maxixcom.otus.booklib.actuator;

import io.github.maxixcom.otus.booklib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookCountHealthIndicator implements HealthIndicator {
    private final BookRepository bookRepository;

    @Override
    public Health health() {
        if (bookRepository.count() == 0) {
            return Health.down()
                    .withDetail("empty", "Library is empty")
                    .build();
        }
        return Health.up().build();
    }
}
