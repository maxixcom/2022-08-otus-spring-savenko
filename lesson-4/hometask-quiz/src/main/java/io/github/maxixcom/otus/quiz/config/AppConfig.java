package io.github.maxixcom.otus.quiz.config;

import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.InputOutputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("io.github.maxixcom.otus.quiz")
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    InputOutputService outputService() {
        return new InputOutputServiceImpl(System.out, System.in);
    }
}
