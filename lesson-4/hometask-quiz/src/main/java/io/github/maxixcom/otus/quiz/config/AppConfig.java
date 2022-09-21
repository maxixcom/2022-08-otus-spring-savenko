package io.github.maxixcom.otus.quiz.config;

import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import io.github.maxixcom.otus.quiz.dao.csv.QuestionCsvResourceLoader;
import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.InputOutputServiceImpl;
import io.github.maxixcom.otus.quiz.service.ScoreCalculator;
import io.github.maxixcom.otus.quiz.service.ScoreCalculatorImpl;
import io.github.maxixcom.otus.quiz.service.TranslationService;
import io.github.maxixcom.otus.quiz.service.TranslationServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QuizConfigProperties.class)
public class AppConfig {
    @Bean
    InputOutputService outputService() {
        return new InputOutputServiceImpl(System.out, System.in);
    }

    @Bean
    TranslationService translationService(MessageSource messageSource, QuizConfigProperties quizConfigProperties) {
        return new TranslationServiceImpl(
                messageSource,
                quizConfigProperties.getLocale()
        );
    }

    @Bean
    QuestionLoader questionLoader(QuizConfigProperties quizConfigProperties) {
        return new QuestionCsvResourceLoader(
                quizConfigProperties.getPath(),
                quizConfigProperties.getLocale()
        );
    }

    @Bean
    ScoreCalculator scoreCalculator(QuizConfigProperties quizConfigProperties) {
        return new ScoreCalculatorImpl(
                quizConfigProperties.getCorrectAnswerRatio()
        );
    }
}
