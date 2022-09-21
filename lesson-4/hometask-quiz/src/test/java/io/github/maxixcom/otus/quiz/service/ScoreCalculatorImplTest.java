package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Score;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCalculatorImplTest {
    @Test
    void shouldPassed() {
        ScoreCalculator scoreCalculator = new ScoreCalculatorImpl(0.75F);
        Score score = scoreCalculator.calculateScore(8, 10);

        assertThat(score.isPassed()).isTrue();
    }

    void shouldFailed() {
        ScoreCalculator scoreCalculator = new ScoreCalculatorImpl(0.75F);
        Score score = scoreCalculator.calculateScore(5, 10);

        assertThat(score.isPassed()).isFalse();
    }
}