package io.github.maxixcom.otus.quiz.domain;

public class Score {
    private final int totalQuestions;
    private final int correctAnswers;
    private final boolean passed;

    public Score(int totalQuestions, int correctAnswers, boolean passed) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.passed = passed;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isPassed() {
        return passed;
    }
}
