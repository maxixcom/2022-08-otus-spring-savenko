package io.github.maxixcom.otus.batch.utils.h2;

import org.h2.tools.Console;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Profile("dev")
@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
@Component
public class H2ConsoleOpenerBean {
    @PostConstruct
    void init() {
        try {
            Console.main();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
