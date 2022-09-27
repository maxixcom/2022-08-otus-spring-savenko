package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.config.provider.QuestionFileProvider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class DaoConfiguration {
    @Bean
    QuestionFileProvider questionFileProvider() {
        return () -> "/questions_en.csv";
    }

    @Bean
    QuestionCsvResourceLoader questionCsvResourceLoader() {
        return new QuestionCsvResourceLoader(questionFileProvider());
    }
}
