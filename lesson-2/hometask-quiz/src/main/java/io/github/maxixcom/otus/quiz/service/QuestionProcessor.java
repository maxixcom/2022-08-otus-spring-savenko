package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Question;

public interface QuestionProcessor {
    QuestionAnswerResult processQuestion(Question question);

    Class<?> getSupportedQuestionClass();
}
