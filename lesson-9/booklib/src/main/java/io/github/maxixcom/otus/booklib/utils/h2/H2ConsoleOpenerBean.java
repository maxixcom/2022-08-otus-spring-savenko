package io.github.maxixcom.otus.booklib.utils.h2;

import org.h2.tools.Console;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

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
