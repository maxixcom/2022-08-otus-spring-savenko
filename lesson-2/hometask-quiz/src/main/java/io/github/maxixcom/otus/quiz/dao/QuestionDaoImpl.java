package io.github.maxixcom.otus.quiz.dao;

import io.github.maxixcom.otus.quiz.domain.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {
    private final QuestionLoader questionLoader;

    public QuestionDaoImpl(QuestionLoader questionLoader) {
        this.questionLoader = questionLoader;
    }

    @Override
    public List<Question> findAll() {
        return questionLoader.load();
    }
}
