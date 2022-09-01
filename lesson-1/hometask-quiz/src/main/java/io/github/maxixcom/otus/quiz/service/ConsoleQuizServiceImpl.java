package io.github.maxixcom.otus.quiz.service;

import io.github.maxixcom.otus.quiz.dao.QuestionDao;
import io.github.maxixcom.otus.quiz.domain.Question;
import io.github.maxixcom.otus.quiz.domain.QuestionChoice;
import io.github.maxixcom.otus.quiz.domain.QuestionGeneral;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleQuizServiceImpl implements ConsoleQuizService {
    private final QuestionDao questionDao;

    public ConsoleQuizServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void printAllQuestions() {
        var questions = questionDao.findAll();

        System.out.println("===============================================");
        System.out.println("#  List of the Quiz questions:");
        System.out.println("-----------------------------------------------");

        if (questions.isEmpty()) {
            System.out.println("There are no questions yet ...");
            return;
        }

        IntStream
                .range(0, questions.size())
                .mapToObj(idx -> (idx + 1) + ". " + formatQuestion(questions.get(idx)))
                .forEach(System.out::println);
    }

    private String formatQuestion(@NonNull Question question) {
        if (question instanceof QuestionGeneral) {
            return question.getContent() + "?";
        }
        if (question instanceof QuestionChoice) {
            List<String> options = ((QuestionChoice) question).getOptions();
            return String.format("%s? (%s)", question.getContent(), String.join(",", options));
        }

        Assert.isTrue(true, "Unknown question type: " + question.getClass().getName());

        return "";
    }
}
