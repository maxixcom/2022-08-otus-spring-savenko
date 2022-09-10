package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.domain.Quiz;
import io.github.maxixcom.otus.quiz.domain.Score;
import io.github.maxixcom.otus.quiz.domain.Student;

public interface QuizService {
    Quiz newQuizForStudent(Student student);
    Score completeQuizAndGetScore(Quiz quiz);
}
