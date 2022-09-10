package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.QuizQuestion;

public interface QuestionProcessor {
    void processQuestion(QuizQuestion quizQuestion);
}
