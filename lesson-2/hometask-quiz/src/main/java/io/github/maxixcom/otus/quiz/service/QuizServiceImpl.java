package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.dao.QuestionDao;
import io.github.maxixcom.otus.quiz.domain.Quiz;
import io.github.maxixcom.otus.quiz.domain.Score;
import io.github.maxixcom.otus.quiz.domain.Student;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionDao questionDao;
    private final ScoreCalculator scoreCalculator;

    public QuizServiceImpl(QuestionDao questionDao, ScoreCalculator scoreCalculator) {
        this.questionDao = questionDao;
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public Quiz newQuizForStudent(Student student) {
        return new Quiz(questionDao.findAll(), student);
    }

    @Override
    public Score completeQuizAndGetScore(Quiz quiz) {
        return scoreCalculator.calculateScore(
                quiz.getQuestionsCount(),
                quiz.getCorrectAnswers()
        );
    }
}
