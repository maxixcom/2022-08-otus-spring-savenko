package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;

public interface OutputService {
    void printHeader();

    void printQuestionGeneral(QuestionGeneral question, Integer index);

    void printQuestionChoice(QuestionChoice question, Integer index);

    void printNoQuestion();
}
