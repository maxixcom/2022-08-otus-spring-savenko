package io.github.maxixcom.otus.quiz.config;

import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.InputOutputServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QuizConfigProperties.class)
public class AppConfig {
    @Bean
    InputOutputService outputService() {
        return new InputOutputServiceImpl(System.out, System.in);
    }

}
