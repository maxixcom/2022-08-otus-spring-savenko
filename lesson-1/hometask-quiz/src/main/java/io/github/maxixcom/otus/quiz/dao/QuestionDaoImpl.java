package io.github.maxixcom.otus.quiz.dao;

import io.github.maxixcom.otus.quiz.domain.Question;

import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    private final List<Question> questions;

    public QuestionDaoImpl(QuestionLoader questionLoader) {
        this.questions = questionLoader.load();
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }
}
