package io.github.maxixcom.otus.booklib.config;

import io.github.maxixcom.otus.booklib.service.io.IOService;
import io.github.maxixcom.otus.booklib.service.io.IOServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }
}
