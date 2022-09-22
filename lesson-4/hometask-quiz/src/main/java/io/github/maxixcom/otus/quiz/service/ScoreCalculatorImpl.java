package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.config.provider.RatioProvider;
import io.github.maxixcom.otus.quiz.domain.Score;
import org.springframework.stereotype.Service;

@Service
public class ScoreCalculatorImpl implements ScoreCalculator {
    private final float correctAnswersRatio;

    public ScoreCalculatorImpl(RatioProvider ratioProvider) {
        this.correctAnswersRatio = ratioProvider.getRatio();
    }

    @Override
    public Score calculateScore(int totalQuestions, int correctAnswers) {
        float correctShare = (float) correctAnswers / totalQuestions;
        boolean passed = correctShare > correctAnswersRatio;
        return new Score(totalQuestions, correctAnswers, passed);
    }
}
