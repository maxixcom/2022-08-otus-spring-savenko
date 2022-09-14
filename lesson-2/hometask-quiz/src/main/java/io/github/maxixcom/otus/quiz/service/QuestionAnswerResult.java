package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Answer;
import io.github.maxixcom.otus.quiz.domain.Question;

public class QuestionAnswerResult {
    private final boolean isCorrectAnswer;

    public QuestionAnswerResult(Question question, Answer answer) {
        this.isCorrectAnswer = question.getCorrectAnswer().equals(answer);
    }

    public boolean isTheAnswerCorrect() {
        return isCorrectAnswer;
    }
}
