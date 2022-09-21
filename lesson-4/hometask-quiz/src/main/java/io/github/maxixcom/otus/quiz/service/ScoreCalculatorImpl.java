package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Score;
import org.springframework.stereotype.Component;

public class ScoreCalculatorImpl implements ScoreCalculator {
    private final float correctAnswersRatio;

    public ScoreCalculatorImpl(Float correctAnswersRatio) {
        this.correctAnswersRatio = correctAnswersRatio;
    }

    @Override
    public Score calculateScore(int totalQuestions, int correctAnswers) {
        float correctShare = (float) correctAnswers / totalQuestions;
        boolean passed = correctShare > correctAnswersRatio;
        return new Score(totalQuestions, correctAnswers, passed);
    }
}
