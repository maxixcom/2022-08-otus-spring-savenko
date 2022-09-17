package io.github.maxixcom.otus.quiz.dao;

import io.github.maxixcom.otus.quiz.domain.Question;

import java.util.List;

public interface QuestionLoader {
    List<Question> load();
}
