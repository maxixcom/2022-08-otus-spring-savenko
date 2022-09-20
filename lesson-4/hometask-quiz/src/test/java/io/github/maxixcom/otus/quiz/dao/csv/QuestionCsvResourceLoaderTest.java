package io.github.maxixcom.otus.quiz.dao.csv;

import io.github.maxixcom.otus.quiz.config.QuizConfigProperties;
import io.github.maxixcom.otus.quiz.dao.QuestionLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionCsvResourceLoaderTest {
    @Mock
    QuizConfigProperties quizConfigProperties;

    @BeforeEach
    void setUp() {
        Mockito.when(quizConfigProperties.getFile()).thenReturn("questions.csv");
    }

    @Test
    void shouldLoadOnlyCorrectLines() {
        QuestionLoader questionLoader = new QuestionCsvResourceLoader(quizConfigProperties);
        var questions = questionLoader.load();

        Assertions.assertThat(questions).size().isEqualTo(2);
    }
}