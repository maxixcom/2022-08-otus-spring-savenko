package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.dao.QuestionDao;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;

import java.util.List;

public class QuizServiceImpl implements QuizService {
    private final QuestionDao questionDao;
    private final OutputService outputService;

    public QuizServiceImpl(QuestionDao questionDao, OutputService outputService) {
        this.questionDao = questionDao;
        this.outputService = outputService;
    }

    @Override
    public void printAllQuestions() {
        List<Question> questions = questionDao.findAll();

        outputService.printHeader();

        if (questions.isEmpty()) {
            outputService.printNoQuestion();
            return;
        }

        int questionIndex = 1;
        for (Question question : questions) {
            if (question instanceof QuestionGeneral) {
                outputService.printQuestionGeneral((QuestionGeneral) question, questionIndex);
            } else if (question instanceof QuestionChoice) {
                outputService.printQuestionChoice((QuestionChoice) question, questionIndex);
            }
        }
    }
}
