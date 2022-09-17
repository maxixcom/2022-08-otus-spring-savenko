package io.github.maxixcom.otus.quiz;

import io.github.maxixcom.otus.quiz.service.InputOutputService;
import io.github.maxixcom.otus.quiz.service.InputOutputServiceImpl;
import io.github.maxixcom.otus.quiz.service.QuestionProcessorManager;
import io.github.maxixcom.otus.quiz.service.processor.QuestionChoiceProcessor;
import io.github.maxixcom.otus.quiz.service.processor.QuestionGeneralProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    InputOutputService outputService() {
        return new InputOutputServiceImpl(System.out, System.in);
    }

    @Bean
    QuestionProcessorManager questionProcessorManager(
            QuestionGeneralProcessor questionGeneralProcessor,
            QuestionChoiceProcessor questionChoiceProcessor
    ) {
        return new QuestionProcessorManager(List.of(
                questionGeneralProcessor,
                questionChoiceProcessor
        ));
    }
}
