package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.config.QuizConfigProperties;
import io.github.maxixcom.otus.quiz.domain.Score;
import org.springframework.stereotype.Component;

@Component
public class ScoreCalculatorImpl implements ScoreCalculator {
    private final float correctAnswersRatio;

    public ScoreCalculatorImpl(QuizConfigProperties quizConfigProperties) {
        this.correctAnswersRatio = quizConfigProperties.getCorrectAnswerRatio();
    }

    @Override
    public Score calculateScore(int totalQuestions, int correctAnswers) {
        float correctShare = (float) correctAnswers / totalQuestions;
        boolean passed = correctShare > correctAnswersRatio;
        return new Score(totalQuestions, correctAnswers, passed);
    }
}
