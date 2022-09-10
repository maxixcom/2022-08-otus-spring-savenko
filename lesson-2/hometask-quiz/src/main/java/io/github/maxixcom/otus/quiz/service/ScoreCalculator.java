package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Score;

public interface ScoreCalculator {
    Score calculateScore(int totalQuestions, int correctAnswers);
}
