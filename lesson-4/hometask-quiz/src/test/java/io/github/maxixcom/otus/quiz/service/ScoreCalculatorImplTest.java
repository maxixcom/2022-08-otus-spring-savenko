package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.config.QuizConfigProperties;
import io.github.maxixcom.otus.quiz.domain.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ScoreCalculatorImplTest {
    @Mock
    QuizConfigProperties quizConfigProperties;

    @BeforeEach
    void setUp() {
        Mockito.when(quizConfigProperties.getCorrectAnswerRatio()).thenReturn(0.75F);
    }

    @Test
    void shouldPassed() {
        ScoreCalculator scoreCalculator = new ScoreCalculatorImpl(quizConfigProperties);
        Score score = scoreCalculator.calculateScore(8, 10);

        assertThat(score.isPassed()).isTrue();
    }

    void shouldFailed() {
        ScoreCalculator scoreCalculator = new ScoreCalculatorImpl(quizConfigProperties);
        Score score = scoreCalculator.calculateScore(5, 10);

        assertThat(score.isPassed()).isFalse();
    }
}